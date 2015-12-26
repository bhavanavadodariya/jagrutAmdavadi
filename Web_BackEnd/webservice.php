<?php
error_reporting(E_ALL); 
date_default_timezone_set("Asia/Kolkata");


$DEBUG            	= 	(!empty($_REQUEST ['DEBUGMODE']))?true:false;
$get            	=    array();
$post            	=    array();
$domainName        	=    "";
$imageUploadDir     =    getcwd()."/images/";


function login()
{
    global $post,$get,$link,$jsonpost;

    if(!empty($jsonpost['email']) && !empty($jsonpost['password']))
    {
        $jsonpost['password']  =    md5($jsonpost['password']);
        $checkQuery        =    "select * from users where username='{$jsonpost['email']}' AND password='{$jsonpost['password']}'";
		
        $checkResult    =    execute_query($checkQuery);
        $checkResultCount=    mysql_num_rows($checkResult);

        if($checkResultCount==1)
        {
            while($row = mysql_fetch_assoc($checkResult))
            {
                $userData    =    array("userId"    =>    $row['id']);
            }

            $retArray    =     array (
                    "status"  => "success",
                    "message" => "",
                    "data"    =>    $userData
            );
        }
        else
        {
            $retArray    =     array (
                    "status" => "error",
                    "message" => "invalid credentials",
            );
        }
    }
    else
    {
        $retArray    =     array (
                "status" => "error",
                "message" => "all field not filled"
        );
    }

    echo encode_respond($retArray);

}


function savedata()
{
    global $post,$get,$link,$jsonpost,$imageUploadDir;

    //func=register&DEBUGMODE=1&email=yashesh@zaptechsolutions.com&fname=yashesh&lname=parma&password=yashesh

    $retArray=array();

    if(!empty($jsonpost['name']) && !empty($jsonpost['location']) && !empty($jsonpost['phonenumber'])&& !empty($jsonpost['image']))
    {
		$imagename	=	generateImageFrom64BitData($jsonpost['image'],$imageUploadDir);
		$description=	!empty($jsonpost['description'])?$jsonpost['description']:"";
		$category	=	!empty($jsonpost['category'])?$jsonpost['category']:"";
		$datetime	=	!empty($jsonpost['datetime'])?$jsonpost['datetime']:date("Y-m-d H:i:s"); 
		
		
		$query    	= "INSERT INTO `yasheshcfa`.`events` (`name`, `location`, `date`, `phonenumber`, `category`, `image`, `description`) VALUES ('{$jsonpost['name']}', '{$jsonpost['location']}', '{$datetime}', '{$jsonpost['phonenumber']}', '{$category}', '$imagename', '{$description}');";
		$result     = execute_query($query);
		$insertedId = mysql_insert_id();

		$checkQuery = "select * from events where id='{$insertedId}'";
		$insertResult = execute_query($checkQuery);

		while ($row = mysql_fetch_assoc($insertResult))
		{
			$userData	=	$row;
		}

		$retArray    =     array (
								"status"    => "success",
								"message"    => "",
								"data"        => $userData
							   );
		
    }
    else
    {
        $retArray    = array (
                                "status" => "error",
                                "message" => "all field not filled"
                               );
    }

    echo encode_respond($retArray);
}

function testing()
{
	global $post,$get,$link,$jsonpost;

	$image=$jsonpost['img'];
	
	$img = imagecreatefromstring(base64_decode($image));
    if($img != false)
    {
		imagejpeg($img, 'images/image'.time().'.jpg');
    } 
	
	$a = array("get"=>$get,"post"=>$post,"jsonpost"=>$jsonpost);  
	echo json_encode($a);
}


function generateImageFrom64BitData($data,$destinationPath="images/")
{
	$img = imagecreatefromstring(base64_decode($data));
	$imagename	=	'image'.time().'.jpg';
    if($img != false)
    {		
		imagejpeg($img, $destinationPath.$imagename);
		return $imagename;
    }
	else
	{
		return ;
	} 	
}

function uploadfile($fileArray,$destinationPath="")
{
    $savedFileName="";

    $target_path = $destinationPath . basename( $fileArray['name']);
    move_uploaded_file($fileArray['tmp_name'], $target_path);

    /* if(move_uploaded_file($fileArray['tmp_name'], $target_path)){ echo "The file " . basename($fileArray['name']) . " has been uploaded";} else { echo "There was an error uploading the file, please try again!";} */

    $savedFileName    =    basename( $fileArray['name']);

    return $savedFileName;
    //return json_encode($fileArray);
}

function generateRandomString($length = 10)
{
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}

function encode_respond($message)
{
    return json_encode ( $message );
}

function execute_query($query)
{
    global $link;
    $result = mysql_query($query,$link) or die('Errant query:  '.$query);

    return $result;
}

function writeTofile($filename,$data)
{
    $myFile = $filename;

    if(file_exists($myFile))
    {
        if (filemtime($myFile) < time() - 864000)
        {
            $f = @fopen($myFile, "r+");
            if ($f !== false)
            {
                ftruncate($f, 0);
            }
        }
    }

    if(!file_exists($myFile))
    {
        touch($myFile);
    }


    $stringData    =    "";
    if(is_array($data))
    {
        $stringData.= date("Y-m-d H:i:s")."<PRE style='font:12px/16px Arial,Helvetica,sans-serif; color:#000000'>";
            $stringData.=    print_r($data,true);
        $stringData.="</PRE>";
    }
    else
    {
        $stringData.=    date("Y-m-d H:i:s") . "<PRE style='font:12px/16px Arial,Helvetica,sans-serif; color:#000000'>$data</PRE>";
    }

    $fh = fopen($myFile, 'a') or die("can't open file");

    fwrite($fh, $stringData);

    fclose($fh);

}
$jsonpost = json_decode(file_get_contents('php://input'), true);
$jsonpost = (!empty($jsonpost))?$jsonpost:array();

writeTofile("request.html",$_REQUEST);
writeTofile("post.html",$_POST);
writeTofile("get.html",$_GET);
writeTofile("files.html",$_FILES);
writeTofile("jsonpost.html",$jsonpost);

if($_SERVER['SERVER_NAME']=='localhost')
{
    $domainName="http://localhost/webservices/bhavana/";
}
else
{
    $domainName="http://yashesh.netai.net/";
}

if ($DEBUG)
{
    //echo "debug mode is on";
    $_POST = $_GET;
}

$functionname="";
if(!empty($jsonpost) && !empty($jsonpost['func']))
{
	$functionname=	$jsonpost['func'];
}
elseif(isset($_REQUEST['func']))
{
	$functionname = $_REQUEST['func'];
}



if (!empty($functionname) && function_exists($functionname))
{
    /* connect to the db */
    if($_SERVER['SERVER_NAME']=='localhost')
    {
      $link = @mysql_connect('localhost','root','') or die('Cannot connect to the DB');
    }
    else
    { 
      $link = @mysql_connect('views2sharecom.ipagemysql.com','yasheshcfa','yasheshcfa') or die('Cannot connect to the DB');
    }

    mysql_select_db('yasheshcfa',$link) or die('Cannot select the DB');

    $func   =    $functionname;
    $get    =    $_GET;
    $post   =    $_POST;
    $files  =    $_FILES;

	
    $func();
}
else
{
    $msg = array (
                    "status" => "error",
                    "message" => "Please pass valid functionality"
                 );
    echo encode_respond ($msg);

    die ();
}

?>
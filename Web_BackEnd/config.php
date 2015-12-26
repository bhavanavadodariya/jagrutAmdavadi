<?php
//$con = mysql_connect("localhost","root","");
//$con = mysql_connect("views2sharecom.ipagemysql.co","yasheshcfa","yasheshcfa");
//$db = mysql_select_db("yasheshcfa",$con);

    if($_SERVER['SERVER_NAME']=='localhost')
    {
      $link = @mysql_connect('localhost','root','') or die('Cannot connect to the DB');
    }
    else
    { 
      $link = @mysql_connect('views2sharecom.ipagemysql.com','yasheshcfa','yasheshcfa') or die('Cannot connect to the DB');
    }

    mysql_select_db('yasheshcfa',$link) or die('Cannot select the DB');

?>
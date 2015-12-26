<?php
require_once('config.php');
$query = mysql_query("select * from events");
while($fetch = mysql_fetch_array($query))
{
	$createdDate	=	date("d-m-Y h:i:s",strtotime($fetch[3]));
	$output[] = array ($fetch[1],$fetch[2],$createdDate,$fetch[4],$fetch[5],"<img src='../images/{$fetch[6]}' height='130px' width='130px'>");
}
echo json_encode($output);
?>
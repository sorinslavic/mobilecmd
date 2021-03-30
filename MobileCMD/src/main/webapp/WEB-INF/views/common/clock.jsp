<head>
<style type="text/css">
	#clock { font-family: Arial, Helvetica, sans-serif; font-size: 1.1em; color: #2A578C; background-color: light-blue; padding: 4px; }
</style>

<script type="text/javascript">
function init () {
	  timeDisplay = document.createTextNode ("");
	  document.getElementById("clock").appendChild ( timeDisplay );
}

function updateClock () {
	  var currentTime = new Date ( );
	  var currentHours = currentTime.getHours ();
	  var currentMinutes = currentTime.getMinutes ();
	  var currentSeconds = currentTime.getSeconds ();
	  currentMinutes = ( currentMinutes < 10 ? "0" : "" ) + currentMinutes;
	  currentSeconds = ( currentSeconds < 10 ? "0" : "" ) + currentSeconds;
	  var timeOfDay = ( currentHours < 12 ) ? "AM" : "PM";
	  currentHours = ( currentHours > 12 ) ? currentHours - 12 : currentHours;
	  currentHours = ( currentHours == 0 ) ? 12 : currentHours; 
	  var currentTimeString = currentHours + ":" + currentMinutes + ":" + currentSeconds + " " + timeOfDay; 
	  document.getElementById("clock").firstChild.nodeValue = currentTimeString;
}
</script>
</head>

<body onload="updateClock(); setInterval('updateClock()', 1000 )">
<br />
	<table width="100%">
		<tr>
			<td>
				Copyright © Sorin Slavic
			</td>
			<td align="right">
				<span id="clock">&nbsp;</span>
			</td>
		</tr>
	</table>
</body>
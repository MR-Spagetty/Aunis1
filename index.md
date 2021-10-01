<!DOCTYPE html>
<html style= "scroll-behavior: smooth;">	                                                                                                                                                                                                                                                                                                                 
	<head>		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		<meta charset="UTF-8">		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		<title>Odkazy || Cruelion.eu	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		</title>		            
		<link rel="icon" type="image/png" href="/grafika/ikon.png"/>		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		<link rel="stylesheet" href="/grafika/bootstrap.min.css">		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		<link rel="stylesheet" href="/grafika/navigace.css">           
		<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
		<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/alertify.min.css"/>
		<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/css/themes/bootstrap.min.css"/>
		<script src="//cdn.jsdelivr.net/npm/alertifyjs@1.13.1/build/alertify.min.js"></script>
		<script src="https://kit.fontawesome.com/2a07ff2acf.js"></script>   
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>   		                                   
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>                                                                                        
	</head>	                                                                                                                                                                                                                                                                                                                 
	<body style="overflow-x: hidden;">                                                   		                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
		<div id="nav">                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			<?php include("config/nav.php")?>                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
		</div>                                                                                                                                                   
		<div id="index" style="<?php if($_SESSION["darkmode"]){ ?>background-color: #7F7F7F; background-image: url('/images/dark-banner.png'); <?php }else{?>background-image: url('/images/light-banner.png');<?php } ?> background-repeat: no-repeat; background-size: cover; background-position: top">
			<div class="container fade-in col-12" align="center" style="font-family: Apple Chancery, cursive; z-index: 100; padding-top: 100px; padding-bottom: 0%; z-index: 10;">                                 
				<font color="white">                    
					<div style="line-height: 70px; font-size: 120px; text-shadow: 0 0 5px black, 0 0 7px black;">Odkazy              
					</div>
				</font>                                                                                                                                                                                                                                                                                                                                                                                                                     
			</div>
			<div class="row" style="padding-bottom: 250px;">
				<div class="container" id="main" style="z-index: 10; display: block; padding-right: 50px; padding-left: 50px; padding-bottom: 50px; padding-top: 15%; border-radius: 5px">   
					<div class="card bg-<?php echo $color1; ?>" style="padding: 15px 15px 15px 15;">
						<p align="center" id="minigames">                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
							<font color="black" size="20px">Požadovaný odkaz nebyl nalezen!
							</font>   <br/>
							(Hledaný odkaz: <?php echo "" . htmlspecialchars($_GET["url"], ENT_QUOTES); ?>)
							<div align="center">
								<a href="/odkazy" style="text-decoration: none;">
									<div class="btn btn-<?php echo $color2; ?> col-6">
										Zpět na hlavní stránku odkazů
									</div>
								</a>
							</div>
						</p>
					</div>
				</div>
			</div>                                                                                                                                                                
		</div>                 
		<button onclick="topFunction()" id="nahoru" style="position: fixed; bottom: 50px; right: 60px; z-index: 99; font-size: 25px;" class="btn btn-<?php echo $color2; ?>"> ▴                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
		</button>                                                                                                                                                                                                                                                                            	                                                                                                                                          
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script>
var mybutton = document.getElementById("nahoru");
window.onscroll = function() {scrollFunction()};
function scrollFunction() {
	if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
	mybutton.style.display = "block";
	} else {
	mybutton.style.display = "none";
	}
}
function topFunction() {
	document.body.scrollTop = 0;
	document.documentElement.scrollTop = 0;
}
</script>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	</body>
</html>
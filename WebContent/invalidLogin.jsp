<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang=""> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Business Service View</title>
    <meta name="description" content="Business Service View">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="apple-touch-icon" href="apple-icon.png">
    <link rel="shortcut icon" href="favicon.ico">

    <link rel="stylesheet" href="resource/assets/css/normalize.css">
    <link rel="stylesheet" href="resource/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="resource/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="resource/assets/css/themify-icons.css">
    <link rel="stylesheet" href="resource/assets/css/flag-icon.min.css">
    <link rel="stylesheet" href="resource/assets/css/cs-skin-elastic.css">
    <link rel="stylesheet" href="resource/assets/scss/style.css">
    <link href='resource/css/opensans.css' rel='stylesheet' type='text/css'>

<style>
html, body { height:100%; }
#username:focus,#password:focus{

box-shadow: inset 4px 0 0 4px darkblue;
}
</style>
</head>
<body class="bg-dark">

<section id="loginform" class="outer-login-wrapper">

  <div class="inner-login-wrapper">
        <div class="container">
            <div class="login-content" style="max-width:350px;">
                <div class="login-logo">

                        <img class="align-content" src="resource/img/bsviewlogo.png" alt="" style="max-width:250px">
						<!--<p style="margin-top:20px;color:#fff;font-size:25px; font-family:'Orbitron',sans-serif;"><strong>BS VIEW</strong></p>-->
                </div>
                <div class="card-body card-block">
                    <form action="LoginServlet" method="post">
                       <div class="form-group">
                            <div class="input-group">
                              <div class="input-group-addon"><i class="fa fa-user"></i></div>
                              <input name="username" class="form-control" id="username" type="text" placeholder="Username">
                            </div>
                       </div>
                       <div class="form-group">
                            <div class="input-group">
                              <div class="input-group-addon"><i class="fa fa-lock"></i></div>
                              <input name="password" class="form-control" id="password" type="password" placeholder="Password">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-block" style="background-color:#337ab7"><strong style="color:white">LOGIN</strong></button>
                        <div><p style="color:red;text-align:center">Invalid Username/Password. Login failed.</p></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    
</section>



</body>
</html>
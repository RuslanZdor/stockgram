<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">

        <!-- Optional theme -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap-theme.min.css" integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>
    </head>

	<body>
        <div class="panel panel-success" style="height:400px; width:400px; margin-bottom: 0px;">
            <div class="panel-heading">
                <span id="panel-title">${company.name}</span>
            </div>
            <div class="panel-body">
                <table class="table">
                    <tbody>
                        <tr>
                            <th scope="row">Current Price</th>
                            <td>${company.companyStats.lastPrice}</td>
                        </tr>
                        <tr>
                            <th scope="row">EPS</th>
                            <td>${company.companyStats.eps}</td>
                        </tr>
                        <tr>
                            <th scope="row">P/E RATIO</th>
                            <td>${company.companyStats.pe}</td>
                        </tr>
                        <tr>
                            <th scope="row">EPS Estimate Current Year</th>
                            <td>${company.companyStats.epsEstimateCurrentYear}</td>
                        </tr>
                        <tr>
                            <th scope="row">Price to Book</th>
                            <td>${company.companyStats.priceBook}</td>
                        </tr>
                        <tr>
                            <th scope="row">Book Per Share</th>
                            <td>${company.companyStats.bookValuePerShare}</td>
                        </tr>
                        <tr>
                            <th scope="row">Short Ratio</th>
                            <td>${company.companyStats.shortRatio}</td>
                        </tr>
                    </tbody>
                </table>
                <div class="date">
                    <span id="date-label">${currentDate}</span>
                </div>
            </div>
        </div>
	</body>
</html>

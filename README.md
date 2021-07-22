# Currency Conversion Service API

## Deployment instructions

* Download the latest Docker image from [Docker hub](https://hub.docker.com/repository/docker/alexnail/currencyconversionservice)
* or build from the sources using the following command:
```
mvn spring-boot:build-image -Dspring-boot.build-image.imageName=<image_name>
````
* Acquire the API key for the remote API currency rates API from https://currencylayer.com
(Note: it seems that the free API provides only USD based pairs rates)
* Run the image using the folloing command
```
docker run -e exchangerate.remote.service.key=<api_key> -p 8080:8080 -it --rm <image_name>
```
In memory basic authentication is used with predefined user:
```
user/password
```

## API Description
All the available API endpoints can be seen using [Swagger UI](http://localhost:8080/swagger-ui.html)

- wallets (operations with wallets)
- commissions (set,get commission percentage for a currency pair )
- exchangerates (get exchange rate for a currency pair)
- transfer (to move money from wallet to wallet)

## Known issues
https://github.com/alexnail/currencyconversionservice/issues

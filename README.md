# kameleoon  
___  
### Application for providing detailed weather information for the desired location  
___  
1. To get started you need to register on the portal https://openweathermap.org/api.  
2. In your personal account, copy the API KEY https://home.openweathermap.org/api_keys. Specify API KEY in application.properties.  
3. The SDK has two types of behavior: on-demand and polling mode. In on-demand mode the
   SDK updates the weather information only on customer requests.  
   In polling mode SDK requests new
   weather information for all stored locations to have zero-latency response for customer requests.  
   Mode of the SDK must be passed as parameter on initialization.  

* For polling mode http://localhost:8080/pollingMode/on.  
* To disable the mode http://localhost:8080/pollingMode/off.  

#### Working with the application
To obtain information about weather conditions in the desired location, you must indicate the name of the locality and the country code according to the ISO 3166 standard:  
http://localhost:8080/pollingMode/new?city=Berlin&countryCode=276  
Сorrect сountry codes https://en.wikipedia.org/wiki/List_of_ISO_3166_country_codes  

The application returns data in JSON format.  
Example:  
```
{
        "coord": {
        "lon": 10.99,
        "lat": 44.34
        },
        "weather": [
        {
        "id": 501,
        "main": "Rain",
        "description": "moderate rain",
        "icon": "10d"
        }
        ],
        "base": "stations",
        "main": {
        "temp": 298.48,
        "feels_like": 298.74,
        "temp_min": 297.56,
        "temp_max": 300.05,
        "pressure": 1015,
        "humidity": 64,
        "sea_level": 1015,
        "grnd_level": 933
        },
        "visibility": 10000,
        "wind": {
        "speed": 0.62,
        "deg": 349,
        "gust": 1.18
        },
        "rain": {
        "1h": 3.16
        },
        "clouds": {
        "all": 100
        },
        "dt": 1661870592,
        "sys": {
        "type": 2,
        "id": 2075663,
        "country": "IT",
        "sunrise": 1661834187,
        "sunset": 1661882248
        },
        "timezone": 7200,
        "id": 3163858,
        "name": "Zocca",
        "cod": 200
        }
```
4. The location allows you to store no more than 10 in memory. When adding a new location, the first one is deleted.  
Please keep this fact in mind. Changing the information update period in the application.properties parameter - task.schedule.delay.    
5. Update about all saved locations in polling mode are automatically updated every 10 minutes.  
   http://localhost:8080/pollingMode
6. Update about all saved locations in on-demand mode occurs when the corresponding method is called.  
   http://localhost:8080/demandMode  

___  
**If you are unable to obtain weather information, the first step to solving the problem is updating the application.**







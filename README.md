# Multipurpose-live-ticker
All purpose ready to go responsive live ticker

Can be used to display
* Downloads ![Downloads](https://i.imgur.com/ITdTXrC.png)
* Daily Average Users
* BTC price

Just change the `getMessage()` function to return the data whatever data
you want to display on the browser

Currently it gets the latest price of BTC and displays that as a placeholder ![placeholder](https://i.imgur.com/yP3nyib.png)

It is responsive so you can have it running up on your startup's office's TVs

## Running details
`mvn clean install`

`mvn package`

`java -jar target/sandbox-1.0-SNAPSHOT-fat.jar`

and then navigate to `localhost:8080/index.html`





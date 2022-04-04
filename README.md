# send-firehose
Simple Spring project in order to send JSON data to a Kinesis delivery stream.

## How to use
After initializing the component, data can be sent by and HTTP request, which is configured by the following steps:

To be able to send data to the data stream, you need to have an authenticated Access Key and the delivery stream's name, sending them as **params** of your request:

```

clientId       - Access Key's client Id
clientSecret   - Access Key's client secret
region         - Region in which the delivery stream is located
deliveryStream - Name of the delivery stream you want to send data to

```

With the authentication configured, you need to send the data in JSON format in the **body** of the request, such as the following:

```JSON
{
  "message" : "hello world!",
  "date" : {
      "hour":  "10",
      "day":   "25",
      "month": "12",
      "year":  "2022"
  }
}

```
Finally, having the data prepared and the authentication params configured, the last step is to send a **POST** request to this endpoint:

```
    http://localhost:8080/sendFirehose
```

If everything goes well, the data sent should appear in Kinesis in a couple of minutes, if it doesn't, check the console of the component for errors regarding authentication or invalid JSON formatting.

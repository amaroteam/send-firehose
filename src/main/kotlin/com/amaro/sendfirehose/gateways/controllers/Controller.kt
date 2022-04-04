package com.amaro.sendfirehose.gateways.controllers

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseAsync
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseAsyncClientBuilder
import com.amazonaws.services.kinesisfirehose.model.PutRecordRequest
import com.amazonaws.services.kinesisfirehose.model.Record
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.nio.ByteBuffer

@RequestMapping
@RestController
class Controller {

    @PostMapping("/sendFirehose")
    fun sendFireHose(
            @RequestBody payload : String,
            @RequestParam clientId : String,
            @RequestParam clientSecret : String,
            @RequestParam region : String,
            @RequestParam deliveryStream : String
    ){
        var firehoseClient = build(region, clientId, clientSecret)
        send(firehoseClient, deliveryStream,payload )
    }

    fun build(regionName : String,
              clientId: String,
              clientSecret : String): AmazonKinesisFirehoseAsync {
        var client  = AmazonKinesisFirehoseAsyncClientBuilder.standard()

        client.withRegion(Regions.fromName(regionName))

        val awsCredentials = BasicAWSCredentials(
                clientId,
                clientSecret
        )
        client.withCredentials(AWSStaticCredentialsProvider(awsCredentials))

        return client.build()
    }

    fun send(firehoseClient: AmazonKinesisFirehoseAsync, deliveryStreamName: String,message: String){

        val putRecordRequest = PutRecordRequest()
        putRecordRequest.deliveryStreamName =
                deliveryStreamName
        putRecordRequest.record = Record().withData(
                ByteBuffer.wrap(message.toByteArray())
        )
        firehoseClient.putRecord(putRecordRequest)
    }
}
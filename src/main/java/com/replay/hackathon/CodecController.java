package com.replay.hackathon;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.replay.hackathon.codec.SecurePayloadCodec;
import io.temporal.api.common.v1.Payload;
import io.temporal.api.common.v1.Payloads;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/v1/decode")
public class CodecController {

    private final SecurePayloadCodec securePayloadCodec = new SecurePayloadCodec();

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> decode(@RequestBody String requestBody) {
        try {
            final long startTime = System.currentTimeMillis();

            Payloads.Builder incomingPayloads = Payloads.newBuilder();
            JsonFormat.parser().merge(requestBody, incomingPayloads);

            List<Payload> incomingPayloadsList = incomingPayloads.build().getPayloadsList();
            List<Payload> outgoingPayloadsList = securePayloadCodec.decode(incomingPayloadsList);


            final String responseBody = JsonFormat.printer()
                    .print(Payloads.newBuilder()
                            .addAllPayloads(outgoingPayloadsList)
                            .build());

            return  ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode payload: " + e.getMessage());
        }
    }
}

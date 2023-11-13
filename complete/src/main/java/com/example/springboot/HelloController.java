package com.example.springboot;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;

@RestController
public class HelloController {

	@GetMapping("/")
	public String index() throws Exception {
		Storage storage = StorageOptions.newBuilder()
				.setCredentials(ServiceAccountCredentials.getApplicationDefault())
				.setProjectId("bubbly-delight-398513")
				.build()
				.getService();

		Blob blob = storage.get("vsh-bucket-1", "gcstest.txt");

		ByteBuffer buff = ByteBuffer.allocate(1024);

		try (ReadChannel readChannel = blob.reader()) {
			readChannel.read(buff);
		}

        String s = new String(buff.array());
		return "Greetings from Spring Boot! " + s;
	}

}

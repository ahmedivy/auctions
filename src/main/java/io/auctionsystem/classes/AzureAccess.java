package io.auctionsystem.classes;

import com.azure.storage.blob.*;
import com.azure.storage.blob.specialized.BlockBlobClient;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AzureAccess {
    static String connectStr;

    static {
        try {
            connectStr = Files.newBufferedReader(Paths.get("credentials.txt")).readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr).buildClient();
    static BlobContainerClient blobDataContainerClient = blobServiceClient.getBlobContainerClient("auctionsystemdata");
    static BlobContainerClient blobImagesClient = blobServiceClient.getBlobContainerClient("auctionsystemimages");

    public static String getJSONString(String name) throws IOException {
        System.out.printf("Indexing %s from Azure Blob Storage%n", name);
        InputStream is = blobDataContainerClient.getBlobClient(name + ".json").openInputStream();
        return streamToStr(is);
    }

    public static String getBlobUrl(String name) {
        BlockBlobClient img = blobImagesClient.getBlobClient(name).getBlockBlobClient();
        return img.getBlobUrl();
    }

    public static String streamToStr(InputStream isr) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int ch; (ch = isr.read()) != -1; ) {
            sb.append((char) ch);
        }
        return sb.toString();
    }

    public static void uploadBlob(File file, String name) {
        BlobClient blobClient = blobImagesClient.getBlobClient(name);
        blobClient.uploadFromFile(file.getAbsolutePath());
    }

    public static void updateBlob(String content, String name) {
        var blobClient = blobDataContainerClient.getBlobClient(name);
        blobClient.upload(new ByteArrayInputStream(
                content.getBytes(StandardCharsets.UTF_8)));
    }

}

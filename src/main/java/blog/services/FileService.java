package blog.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    // Saves the uploaded file to the given folder and returns the generated file name
    String uploadImage(String path, MultipartFile file) throws IOException;

    // Reads a file back from disk so it can be served to the client
    InputStream getResource(String path, String fileName) throws FileNotFoundException;
}
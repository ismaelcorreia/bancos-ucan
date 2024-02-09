package edu.ucan.sdp2.bancocore.utils;


import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Component
public class ManipuladorFicheiroUtil {
    private final String USER_PATH = "/users";
    private final String ROOT_PATH = "public";
    private final String STORAGE_PATH = "/storage";
    private final HttpServletRequest request;

    @Autowired
    public ManipuladorFicheiroUtil(HttpServletRequest request) {
        this.request = request;
    }
    @SneakyThrows
    public String salvarImagemPerfil(String nomePerfil, MultipartFile file) {
        var directoryFile = new File(ROOT_PATH + STORAGE_PATH + USER_PATH + "/" + nomePerfil + "/profile/");

        if (!directoryFile.exists()) {
            directoryFile.mkdirs();
        }

        String fileName = String.format("%s.png", ManipuladorTextoUtil.dateTimeNowYYYMMddHHmmss());
        Path path = Paths.get(directoryFile.getAbsolutePath(),fileName);
        Files.write(path, file.getBytes());
        return STORAGE_PATH + USER_PATH + "/" + nomePerfil + "/profile/"+fileName;
    }

    private static File fromMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }
        return file;
    }

    public String getUrlFileFromCurrentContext(String urlPath) {
        if (urlPath.toLowerCase().startsWith("http")) {
            return urlPath;
        }
        String context = request.getRequestURL().substring(0, request.getRequestURL().indexOf(request.getServletPath()));
        return context + urlPath;
    }

    @SneakyThrows
    public boolean replace(String path, MultipartFile file) {
        var directoryFile = new File(ROOT_PATH + path);
        if (!directoryFile.exists()) {
            return false;
        }
        Files.write(Paths.get(directoryFile.getAbsolutePath()), file.getBytes());
        return true;
    }


}

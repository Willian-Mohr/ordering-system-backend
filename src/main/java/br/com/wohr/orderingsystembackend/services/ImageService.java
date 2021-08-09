package br.com.wohr.orderingsystembackend.services;

import br.com.wohr.orderingsystembackend.services.exceptions.FileException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {

        String extensionFile = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());

        if (!"png".equals(extensionFile) && !"jpg".equals(extensionFile)) {
            throw new FileException("Somente imagens do tipo PNG e JPG são permitidas");
        }

        try {
            BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
            if ("png".equals(extensionFile)) {
                img = pngToJpg(img);
            }

            return img;
        } catch (IOException e) {
            throw new FileException("Erro ao ler aquivo");
        }
    }

    public BufferedImage pngToJpg(BufferedImage img) {

        // https://mkyong.com/java/convert-png-to-jpeg-image-file-in-java/
        // jpg needs BufferedImage.TYPE_INT_RGB
        BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        jpgImage.createGraphics().drawImage(img, 0, 0, Color.white, null);

        return jpgImage;
    }

    public InputStream getInputStream(BufferedImage img, String extension) {

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, extension, os);

            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }
    }
}

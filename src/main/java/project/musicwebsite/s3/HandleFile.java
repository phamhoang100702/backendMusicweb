package project.musicwebsite.s3;

import lombok.Data;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.UUID;


@Component
public class HandleFile {

    public HandleFile() {
    }

    private boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        System.out.println("extension " + fileExtension);
        return Arrays.asList(new String[]{"mp3"})
                .contains(fileExtension.trim().toLowerCase());
    }

    public String storeSoundFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file");
        }
        if (!isImageFile(file)) {
            throw new RuntimeException("You can only upload sound image");
        }
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String generatedFile = UUID.randomUUID().toString().replace("-", "");
        generatedFile += "." + fileExtension;
        return   generatedFile;

    }


    private boolean isLyricFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"jpg"})
                .contains(fileExtension.trim().toLowerCase());
    }

    public String storeLyricsFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file");
        }
        if (!isImageFile(file)) {
            throw new RuntimeException("You can only upload file lyrics");
        }
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String generatedFile = UUID.randomUUID().toString().replace("-", "");
        generatedFile += "." + fileExtension;
        return   generatedFile;

    }
}

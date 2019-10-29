package formdata;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileFormData {
	private MultipartFile file;
}

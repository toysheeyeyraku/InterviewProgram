package formdata;

import lombok.Data;

@Data
public class AddUserBody {
	private String username;
	private String password;
}

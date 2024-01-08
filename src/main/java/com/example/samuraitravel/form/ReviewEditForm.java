package com.example.samuraitravel.form;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewEditForm {
		@NotNull
		private Integer id;
		
	    @NotBlank(message = "お名前をどうぞ")
	    private String name;
	         
	    @NotBlank(message = "感想を入力してください。")
	    private String commentText;   
	   
	    @NotNull(message = "点数を５点満点で入力してください。")
	    @Min(value = 1, message = "０点以上５点以下")
	    private Integer value;        
    
}

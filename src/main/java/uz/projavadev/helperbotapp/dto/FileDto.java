package uz.projavadev.helperbotapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private String file_id;
    private String file_unique_id;
    private Integer file_size;
    private String file_path;
}

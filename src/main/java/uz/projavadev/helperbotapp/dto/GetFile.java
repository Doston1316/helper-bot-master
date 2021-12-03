package uz.projavadev.helperbotapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFile {

    private boolean ok;

    private FileDto result;

}

package ie.irishrail.server.base.component;

import ie.irishrail.server.base.common.ServerResponseConstants;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ie.irishrail.server.base.entity.FileBucket;

@Component
public class FileValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return FileBucket.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        FileBucket file = (FileBucket) obj;

        if (file.getFile() != null && file.getFile().getSize() == 0) {
            errors.rejectValue("file", "missing.file", ServerResponseConstants.MISSING_FILE_ON_FILEUPLOAD_TEXT);
        }
    }
}

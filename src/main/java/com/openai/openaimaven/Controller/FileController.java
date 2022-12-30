package com.openai.openaimaven.Controller;

import com.openai.openaimaven.DTO.RequestDTO.FileUploadDTO;
import com.openai.openaimaven.DTO.Wrapper.ResponseWrapper;
import com.openai.openaimaven.Services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@RestController
@RequestMapping("/files")
public class FileController extends ApiRestHandler{

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/upload",method = RequestMethod.POST,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseWrapper uploadFile(@RequestPart("file")MultipartFile file, @RequestBody FileUploadDTO fileUploadDTO) {
        return ResponseWrapper.getSuccessResponse(Collections.singletonMap("File Uploaded", fileService.uploadFineTuneFile(file,fileUploadDTO)), "Details of Uploaded File");
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public ResponseWrapper listOfFiles()
    {
        return ResponseWrapper.getSuccessResponse(Collections.singletonMap("Files",fileService.getListOfFiles()),"List of Files Uploaded");
    }

    @RequestMapping(value = "delete/{fileId}",method = RequestMethod.DELETE)
    public ResponseWrapper deleteFiles(@PathVariable String fileId)
    {
        return ResponseWrapper.getSuccessResponse(Collections.singletonMap("Deleted File",fileService.deleteFile(fileId)),"File Delete");
    }

    @RequestMapping(value = "deleteAll",method = RequestMethod.DELETE)
    public ResponseWrapper deleteAllFiles()
    {
        return ResponseWrapper.getSuccessResponse(Collections.singletonMap("Deleted Files",fileService.deleteAllFileRequest()),"List of Files Deleted");
    }

}

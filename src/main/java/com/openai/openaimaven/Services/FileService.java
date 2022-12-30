package com.openai.openaimaven.Services;



import com.openai.openaimaven.DTO.RequestDTO.FileUploadDTO;
import com.openai.openaimaven.Entity.UploadedFiles;
import com.openai.openaimaven.OpenAITemplate.OpenAIRestTemplate;
import com.openai.openaimaven.Repository.UploadedFilesRepository;
import com.openai.openaimaven.Utility.ParserUtility;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;

@Service
@Slf4j
public class FileService {

    @Autowired
    private UploadedFilesRepository uploadedFilesRepository;

    @Autowired
    private OpenAIRestTemplate openAIRestTemplate;

    @SneakyThrows
    public void fileValidator(MultipartFile file)
    {
        if(file.isEmpty()) {
            throw new IOException("File is empty");
        }
        if(!StringUtils.getFilenameExtension(file.getOriginalFilename()).equals("jsonl")) {
            throw new IOException("File extension must be of type jsonl");
        }
    }

    @SneakyThrows
    public JSONObject uploadFineTuneFile(MultipartFile file, FileUploadDTO fileUploadDTO){
        fileValidator(file);
        ByteArrayResource byteArrayResource ;
        try {
            byteArrayResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
        }
        catch (IOException io)
        {
            throw new IOException("Unable to handle file");
        }
        MultiValueMap<String,Object> body = new LinkedMultiValueMap<>();
        body.add("purpose","fine-tune");
        body.add("file", byteArrayResource);
        String response = openAIRestTemplate.makeRequest("/files",body,null,HttpMethod.POST);
        JSONObject responseObject = ParserUtility.stringToJson(response);
        saveFileInfo(responseObject,fileUploadDTO);
        return responseObject;
    }

    private void saveFileInfo(JSONObject jsonObject,FileUploadDTO fileUploadDTO)
    {
        UploadedFiles uploadedFiles = new UploadedFiles();
        uploadedFiles.setFileId(jsonObject.get("id").toString());
        uploadedFiles.setPurpose(jsonObject.get("purpose").toString());
        uploadedFiles.setFilename(jsonObject.get("filename").toString());
        uploadedFiles.setBytes(jsonObject.get("bytes").toString());
        uploadedFiles.setUploadedOnOpenAi(jsonObject.get("created_at").toString());
        uploadedFiles.setStatus(jsonObject.get("status").toString());

        if(fileUploadDTO.getRemarks()!=null && !fileUploadDTO.getRemarks().isEmpty()) {
            uploadedFiles.setRemarks(fileUploadDTO.getRemarks());
        }
        if(fileUploadDTO.getAdditionalInformation()!=null && !fileUploadDTO.getAdditionalInformation().isEmpty()) {
            uploadedFiles.setAdditionalInformation(fileUploadDTO.getAdditionalInformation());
        }
        uploadedFilesRepository.save(uploadedFiles);
    }

    public JSONObject getListOfFiles()
    {
     return ParserUtility.stringToJson(openAIRestTemplate.makeRequest("/files",null,null,HttpMethod.GET));
    }

    public JSONObject deleteFile(String fileId)
    {
        return ParserUtility.stringToJson(openAIRestTemplate.makeRequest("/files/"+fileId,null,null,HttpMethod.DELETE));
    }

    public ArrayList deleteAllFileRequest()
    {
        ArrayList<JSONObject> deleteFilesList = new ArrayList();
        ArrayList<JSONObject> listOfFiles = (ArrayList) getListOfFiles().get("data");
        for(JSONObject file : listOfFiles)
        {
            deleteFilesList.add(deleteFile(file.get("id").toString()));
        }
        return deleteFilesList;
    }

}

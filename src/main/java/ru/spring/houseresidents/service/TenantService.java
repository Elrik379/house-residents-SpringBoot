package ru.spring.houseresidents.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.stereotype.Service;
import ru.spring.houseresidents.model.Tenant;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@JsonAutoDetect
public class TenantService {

    static {
        try {
            loadTenants();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @JsonDeserialize(as = ArrayList.class)
    private static List<Tenant> tenantList;

    public TenantService() throws IOException {
    }


    public List<Tenant> getTenantList() {
        return tenantList;
    }

    public void addTenant(Tenant tenant){
        tenantList.add(tenant);
    }

    public void saveTenantList() throws IOException {
        try (FileWriter fileWriter = new FileWriter("Save.txt")) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(fileWriter, tenantList);
        }
    }

    public static void loadTenants() throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("Save.txt"))) {
            String base = bufferedReader.readLine();
            if(base == null){
                tenantList = new ArrayList<>(); //возврат пустого листа, если данных в Save.txt нет
                return;
            }
            ObjectMapper mapper = new ObjectMapper();
            CollectionType type = TypeFactory.defaultInstance()
                    .constructCollectionType(ArrayList.class, Tenant.class); //помогает избежать ошибки при десериализации в лист
            tenantList = mapper.readValue(base, type);
        }
    }
}

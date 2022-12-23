package ru.spring.houseresidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.spring.houseresidents.model.Tenant;
import ru.spring.houseresidents.service.TenantService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/tenants")
    public String viewTenants(Model model){
        List<Tenant> tenants = tenantService.getTenantList();
        model.addAttribute("tenants", tenants);
        return "/tenants";
    }

    @PostMapping(path = "/tenants")
    public String addTenant(@RequestParam String name,
                            @RequestParam int year,
                            @RequestParam int room, Model model) throws IOException {
        Tenant tenant = new Tenant();
        tenant.setName(name);
        tenant.setYear(year);
        tenant.setRoom(room);
        tenantService.addTenant(tenant);
        tenantService.saveTenantList();
        List<Tenant> tenants = tenantService.getTenantList();
        model.addAttribute("tenants", tenants);
        return "/tenants";

    }
}

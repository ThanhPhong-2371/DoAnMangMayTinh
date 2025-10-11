package com.webserver.webbanhang.controller.User;

import com.webserver.webbanhang.model.Brand;
import com.webserver.webbanhang.repository.BrandRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
@RequestMapping("/brands")
public class BrandController {

    @Autowired
    private BrandRepository brandRepository;
    
    
    @GetMapping
    public List<Brand> getAllBrands(){
        return  brandRepository.findAll();
    }

   
}

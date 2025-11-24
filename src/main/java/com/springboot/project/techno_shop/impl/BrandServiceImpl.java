package com.springboot.project.techno_shop.impl;

import com.springboot.project.techno_shop.entity.Brand;
import com.springboot.project.techno_shop.exception.NotFoundException;
import com.springboot.project.techno_shop.repository.BrandRepository;
import com.springboot.project.techno_shop.service.BrandService;
import com.springboot.project.techno_shop.spec.BrandFilter;
import com.springboot.project.techno_shop.spec.BrandSpec;
import com.springboot.project.techno_shop.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public Brand create(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand getById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Brand", id));
    }

    @Override
    public Brand update(Long id, Brand updateBrand) {
         Brand brand = getById(id);
         brand.setName(updateBrand.getName());
        return brandRepository.save(brand);
    }

//    @Override
//    public List<Brand> getBrands(Map<String, String> params) {
//        BrandFilter brandFilter = new BrandFilter();
//
//        if (params.containsKey("name")){
//            String name = params.get("name");
//            brandFilter.setName(name);
//        }
//        if (params.containsKey("id")){
//            String id = params.get("id");
//            brandFilter.setId(Long.parseLong(id));
//        }
//
//        BrandSpec brandSpec = new BrandSpec(brandFilter);
//
//        return brandRepository.findAll(brandSpec, pageable);
//    }

    @Override
    public Page<Brand> getBrands(Map<String, String> params) {
        BrandFilter brandFilter = new BrandFilter();

        if (params.containsKey("name")){
            String name = params.get("name");
            brandFilter.setName(name);
        }
        if (params.containsKey("id")){
            String id = params.get("id");
            brandFilter.setId(Long.parseLong(id));
        }

        int pageNumber = PageUtil.DEFAULT_PAGE_NUMBER;
        if (params.containsKey(PageUtil.PAGE_NUMBER)){
            pageNumber = Integer.parseInt(params.get(PageUtil.PAGE_NUMBER));
        }

        int pageLimit = PageUtil.DEFAULT_PAGE_LIMIT;
        if (params.containsKey(PageUtil.PAGE_LIMIT)){
            pageLimit = Integer.parseInt(params.get(PageUtil.PAGE_LIMIT));
        }

        BrandSpec brandSpec = new BrandSpec(brandFilter);
        Pageable pageable = PageUtil.getPageable(pageNumber, pageLimit);

        Page<Brand> page = brandRepository.findAll(brandSpec, pageable);
        return page;
    }

    @Override
    public void destroy(Long id) {
        Brand brandDelete = getById(id);
        brandRepository.delete(brandDelete);
    }


//    @Override
//    public List<Brand> getBrands() {
//        return brandRepository.findAll();
//    }

//    @Override
//    public List<Brand> getBrands(String name) {
//        return brandRepository.findByNameContaining(name);
//    }

}

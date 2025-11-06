package com.example.simsppob.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.simsppob.models.Banner;
import com.example.simsppob.models.Service;
import com.example.simsppob.repositories.BannerRepository;
import com.example.simsppob.repositories.ServiceRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class InitialDataLoader implements ApplicationRunner {
    
    @Autowired
    private BannerRepository bannerRepository;
    
    @Autowired
    private ServiceRepository serviceRepository;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        initBanners();
        initServices();
    }
    
    private void initBanners() {
        if (bannerRepository.count() == 0) {
            List<Banner> banners = Arrays.asList(
                createBanner("Banner 1", "https://minio.nutech-integrasi.com/take-home-test/banner/Banner-1.png", "Lerem Ipsum Dolor sit amet"),
                createBanner("Banner 2", "https://minio.nutech-integrasi.com/take-home-test/banner/Banner-2.png", "Lerem Ipsum Dolor sit amet"),
                createBanner("Banner 3", "https://minio.nutech-integrasi.com/take-home-test/banner/Banner-3.png", "Lerem Ipsum Dolor sit amet"),
                createBanner("Banner 4", "https://minio.nutech-integrasi.com/take-home-test/banner/Banner-4.png", "Lerem Ipsum Dolor sit amet"),
                createBanner("Banner 5", "https://minio.nutech-integrasi.com/take-home-test/banner/Banner-5.png", "Lerem Ipsum Dolor sit amet")
            );
            bannerRepository.saveAll(banners);
            System.out.println("Initial banners data loaded successfully!");
        } else {
            System.out.println("Banners data already exists, skipping initialization.");
        }
    }
    
    private void initServices() {
        if (serviceRepository.count() == 0) {
            List<Service> services = Arrays.asList(
                createService("PAJAK", "Pajak PBB", "https://nutech-integrasi.app/dummy.jpg", 40000L),
                createService("PLN", "Listrik", "https://nutech-integrasi.app/dummy.jpg", 10000L),
                createService("PDAM", "PDAM Berlangganan", "https://nutech-integrasi.app/dummy.jpg", 40000L),
                createService("PULSA", "Pulsa", "https://nutech-integrasi.app/dummy.jpg", 40000L),
                createService("PGN", "PGN Berlangganan", "https://nutech-integrasi.app/dummy.jpg", 50000L),
                createService("MUSIK", "Musik Berlangganan", "https://nutech-integrasi.app/dummy.jpg", 50000L),
                createService("TV", "TV Berlangganan", "https://nutech-integrasi.app/dummy.jpg", 50000L),
                createService("PAKET_DATA", "Paket data", "https://nutech-integrasi.app/dummy.jpg", 50000L),
                createService("VOUCHER_GAME", "Voucher Game", "https://nutech-integrasi.app/dummy.jpg", 100000L),
                createService("VOUCHER_MAKANAN", "Voucher Makanan", "https://nutech-integrasi.app/dummy.jpg", 100000L),
                createService("QURBAN", "Qurban", "https://nutech-integrasi.app/dummy.jpg", 200000L),
                createService("ZAKAT", "Zakat", "https://nutech-integrasi.app/dummy.jpg", 300000L)
            );
            serviceRepository.saveAll(services);
            System.out.println("Initial services data loaded successfully!");
        } else {
            System.out.println("Services data already exists, skipping initialization.");
        }
    }
    
    private Banner createBanner(String name, String image, String description) {
        Banner banner = new Banner();
        banner.setBannerName(name);
        banner.setBannerImage(image);
        banner.setDescription(description);
        return banner;
    }
    
    private Service createService(String code, String name, String icon, Long tariff) {
        Service service = new Service();
        service.setServiceCode(code);
        service.setServiceName(name);
        service.setServiceIcon(icon);
        service.setServiceTariff(tariff);
        return service;
    }
}
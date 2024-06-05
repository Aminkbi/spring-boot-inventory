package com.aminkbi.learnspring.services;


import com.aminkbi.learnspring.dtos.stockEntries.StockEntriesDTO;
import com.aminkbi.learnspring.dtos.stockEntries.StockEntriesResponseDTO;
import com.aminkbi.learnspring.exceptions.NotFoundException;
import com.aminkbi.learnspring.models.StockEntries;
import com.aminkbi.learnspring.models.Product;
import com.aminkbi.learnspring.repositories.StockEntriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockEntriesService {

    private final StockEntriesRepository stockEntriesRepository;

    @Autowired
    public StockEntriesService(StockEntriesRepository stockEntriesRepository) {
        this.stockEntriesRepository = stockEntriesRepository;
    }

    public StockEntriesResponseDTO addStockEntries(StockEntriesDTO stockEntriesDTO) {
        StockEntries stockEntries = stockEntriesRepository.save(this.mapToStockEntries(stockEntriesDTO));
        return mapToDTO(stockEntries);
    }

    public StockEntriesResponseDTO getStockEntriesById(Long id) {
        StockEntries stockEntries = stockEntriesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("StockEntries not found with id: " + id));
        return mapToDTO(stockEntries);
    }

    public StockEntriesResponseDTO updateStockEntries(Long id, StockEntriesDTO stockEntriesDTO) {
         stockEntriesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("StockEntries not found with id: " + id));
        StockEntries stockEntries = mapToStockEntries(stockEntriesDTO);
        stockEntries.setId(id);
        return mapToDTO(stockEntriesRepository.save(stockEntries));
    }

    public void deleteStockEntriesById(Long id) {
        if (!stockEntriesRepository.existsById(id)) {
            throw new NotFoundException("StockEntries not found with id: " + id);
        }
        stockEntriesRepository.deleteById(id);
    }

    public List<StockEntriesResponseDTO> getAllStockEntries(Integer page, Integer pageSize) {
        return stockEntriesRepository.findAll(Pageable.ofSize(pageSize).withPage(page)).stream().map(this::mapToDTO).collect(Collectors.toList());
    }


    private StockEntries mapToStockEntries(StockEntriesDTO stockEntriesDTO) {
        StockEntries stockEntries = new StockEntries();
        Product product = new Product();
        product.setId(stockEntriesDTO.getProductId());


        stockEntries.setProduct(product);
        stockEntries.setChangeType(stockEntriesDTO.getChangeType());
        stockEntries.setChangeQuantity(stockEntriesDTO.getChangeQuantity());

        return stockEntries;
    }

    private StockEntriesResponseDTO mapToDTO(StockEntries stockEntries) {
        StockEntriesResponseDTO stockEntriesResponseDTO = new StockEntriesResponseDTO();
        stockEntriesResponseDTO.setChangeDate(stockEntries.getChangeDate());
        stockEntriesResponseDTO.setChangeType(stockEntries.getChangeType());
        stockEntriesResponseDTO.setChangeQuantity(stockEntries.getChangeQuantity());
        stockEntriesResponseDTO.setProduct(stockEntries.getProduct());
        return stockEntriesResponseDTO;
    }

}

package com.yash.project.repo;

import com.yash.project.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepo extends JpaRepository<Seller,Integer> {

}

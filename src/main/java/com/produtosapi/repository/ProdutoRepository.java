package com.produtosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.produtosapi.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
}

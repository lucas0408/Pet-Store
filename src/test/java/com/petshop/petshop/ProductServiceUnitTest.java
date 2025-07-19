package com.petshop.petshop;

import com.petshop.petshop.DTO.ProductDTO;
import com.petshop.petshop.DTO.ProductResponseDTO;
import com.petshop.petshop.exception.ResourceNotFoundException;
import com.petshop.petshop.model.Category;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.repository.CategoryRepository;
import com.petshop.petshop.repository.ProductRepository;
import com.petshop.petshop.service.ImageService;
import com.petshop.petshop.service.ProductServiceImpl;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Should get all products")
    public void getAllProductsHappyFlow(){
        List<Product> produtos = new ArrayList<>();

        Product product = createProduct();
        produtos.add(product);

        List<ProductResponseDTO> productsDTO = new ArrayList<>();
        productsDTO.add(new ProductResponseDTO(product));

        given(productRepository.findAll()).willReturn(produtos);

        List<ProductResponseDTO> getAllProducts = productService.getAllProducts();

        assertEquals(productsDTO, getAllProducts);
    }

    @Test
    @DisplayName("Should get product by id")
    public void getProductByIdHappyFlow(){

        Product product = createProduct();

        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));

        ProductResponseDTO productDTO = new ProductResponseDTO(product);

        ProductResponseDTO getProductsDTO = productService.getProduct(product.getId());

        assertEquals(getProductsDTO, productDTO);
    }

    @Test
    @DisplayName("Should not get product by id")
    public void NotGetProductById(){


        given(productRepository.findById("f47ac10b-58cc-4372-a567-0e02b2c3d479")).willReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.getProduct("f47ac10b-58cc-4372-a567-0e02b2c3d479")
        );
    }

    @Test
    @DisplayName("Should create a product")
    public void CreateProductByHappyFlow(){

        ProductDTO productDTO = createProductDTO();
        Product product = createProduct();

        product.setCategories(Collections.emptySet());
        productDTO.setCategories(Collections.emptySet());

        given(productRepository.existsByName(product.getName())).willReturn(false);
        given(productRepository.save(any(Product.class))).willReturn(product);

        ProductResponseDTO productResponseDTO = productService.createProduct(productDTO);

        System.out.println(product);

        assertEquals(productResponseDTO, new ProductResponseDTO(product));

    }

    @Test
    @DisplayName("Should create a product with category")
    public void CreateProductWithCategory(){
        ProductDTO productDTO = createProductDTO();
        Product product = createProduct();

        Iterator<Category> iterator = productDTO.getCategories().iterator();
        Category category1 = iterator.next();
        Category category2 = iterator.next();

        given(productRepository.existsByName(product.getName())).willReturn(false);
        given(categoryRepository.findById(category1.getId())).willReturn(Optional.of(category1));
        given(categoryRepository.findById(category2.getId())).willReturn(Optional.of(category2));
        given(productRepository.save(any(Product.class))).willReturn(product);

        ProductResponseDTO productResponseDTO = productService.createProduct(productDTO);

        assertEquals(productResponseDTO, new ProductResponseDTO(product));
        assertEquals(productResponseDTO.categories(), new ProductResponseDTO(product).categories());
    }

    @Test
    @DisplayName("Product with exist name in DB")
    public void CreateProductWithExistName(){

        ProductDTO productDTO = createProductDTO();

        given(productRepository.existsByName(productDTO.getName())).willReturn(true);

        assertThrows(
                ValidationException.class,
                () -> productService.createProduct(productDTO)
        );

        verify(productRepository, never())
                .save(any(Product.class));
    }

    @Test
    @DisplayName("Should update a product")
    public void UpdateProductByHappyFlow(){

        ProductDTO updateProductDTO = createProductDTO();

        Product product = createProduct();

        product.setCategories(Collections.emptySet());
        updateProductDTO.setCategories(Collections.emptySet());

        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(productRepository.save(any(Product.class))).willReturn(product);

        ProductResponseDTO productResponseDTO = productService.updateProduct(product.getId(), updateProductDTO);

        assertEquals(productResponseDTO, new ProductResponseDTO(product));
    }

    @Test
    @DisplayName("Should update a product with category")
    public void UpdateProductWithCategory(){

        ProductDTO updateProductDTO = createProductDTO();

        Product product = createProduct();

        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(productRepository.save(any(Product.class))).willReturn(product);

        ProductResponseDTO productResponseDTO = productService.updateProduct(product.getId(), updateProductDTO);

        assertEquals(productResponseDTO, new ProductResponseDTO(product));
    }

    @Test
    @DisplayName("Should not update a product with invalid ID")
    public void UpdateWithInvalidId(){

        ProductDTO updateProductDTO = createProductDTO();

        Product product = createProduct();

        given(productRepository.findById(product.getId())).willReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.updateProduct(product.getId(), updateProductDTO)
        );

        verify(productRepository, never())
                .save(any(Product.class));
    }

    @Test
    @DisplayName("Should delete a Product")
    public void DeleteProductWithValidID(){

        Product product = createProduct();

        given(productRepository.findById(product.getId())).willReturn(Optional.of(product));

        productService.deleteProduct(product.getId());

        verify(productRepository)
                .deleteById(product.getId());
    }

    @Test
    @DisplayName("Should not delete a Product")
    public void DeleteProductWithInvalidID(){

        Product product = createProduct();

        given(productRepository.findById(product.getId())).willReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.deleteProduct(product.getId())
        );

        verify(productRepository, never())
                .deleteById(product.getId());
    }


    public ProductDTO createProductDTO(){
        ProductDTO dto = new ProductDTO();
        dto.setName("Ração Premium");
        dto.setUnitPrice(new BigDecimal("89.90"));
        dto.setUnitsInStock(50);
        dto.setImageUrl("any");
        dto.setCategories(setCategories());
        return dto;
    }

    public Set<Category> setCategories(){
        Category category1 = getCategory1();

        Category category2 = getCategory2();

        Set<Category> categories = new HashSet<>();
        categories.add(category1);
        categories.add(category2);

        return categories;
    }

    public Category getCategory1(){
        Category categoria1 = new Category();
        categoria1.setId(UUID.randomUUID().toString());
        categoria1.setName("Alimentos");
        return categoria1;
    }

    public Category getCategory2(){
        Category categoria2 = new Category();
        categoria2.setId(UUID.randomUUID().toString());
        categoria2.setName("Pets");
        return categoria2;
    }

    public Product createProduct(){
        Product product = new Product();

        product.setId("123e4567-e89b-12d3-a456-426614174000");
        product.setName("Ração Premium");
        product.setUnitPrice(new BigDecimal("89.90"));
        product.setUnitsInStock(50);
        product.setImageUrl("https://meusite.com/imagens/racao-premium.png");

        product.setCategories(setCategories());

        return product;
    }
}

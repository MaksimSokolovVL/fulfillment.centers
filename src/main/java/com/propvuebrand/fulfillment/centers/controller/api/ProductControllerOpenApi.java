package com.propvuebrand.fulfillment.centers.controller.api;

import com.propvuebrand.fulfillment.centers.domain.ProductStatus;
import com.propvuebrand.fulfillment.centers.domain.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "ProductController", description = "")
public interface ProductControllerOpenApi {
    @Operation(
            summary = "Получение продукта по ID",
            description = """
                    ...
                    Параметры запроса:
                    @param id - id продукта.
                    @return ProductDto в формате JSON.
                    """,
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ с ProductDto",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDto.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<ProductDto> getProduct(
            @Parameter(
                    description = "Уникальный идентификатор продукта",
                    required = true,
                    example = "1"
            )
            @PathVariable("id") Long id);

    @Operation(
            summary = "Получение списка всех продуктов",
            description = """
                    Возвращает страницу с продуктами в формате JSON.
                    Параметры запроса:
                    page - номер страницы (по умолчанию 0),
                    size - количество элементов на странице (по умолчанию 10).
                    @return Page<ProductDto> в формате JSON.
                    """,
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ с страницей ProductDto",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object", implementation = Page.class)
                            )
                    )
            }
    )
    @GetMapping
    ResponseEntity<Page<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size);

    @Operation(
            summary = "Добавление нового продукта",
            description = "Добавляет новый продукт в систему.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Пример тела запроса для добавления продукта",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class),
                            examples = @ExampleObject(
                                    name = "Пример продукта",
                                    summary = "Дефолтный объект для создания продукта",
                                    value = """
                                            {
                                                "product": "p1",
                                                "status": "SELLABLE",
                                                "fulfillmentCenter": "fc1",
                                                "qty": 10,
                                                "value": 100.0
                                            }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ с ProductDto",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDto.class)
                            )
                    )
            }
    )
    @PostMapping
    ResponseEntity<ProductDto> postCreateProduct(@RequestBody ProductDto productDto);

    @Operation(
            summary = "Обновление продукта",
            description = "..." +
                    "Параметры запроса: " +
                    "@param id, productDto  - id продукта и новые значения ProductDto " +
                    "@return ProductDto в формате JSON.",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ с ProductDto",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ProductDto.class)
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<ProductDto> putUpdateProduct(@PathVariable("id") Long id, @RequestBody ProductDto productDto);

    @Operation(
            summary = "Удаление продукта по ID",
            description = "..." +
                    "Параметры запроса: " +
                    "@param id  - id продукта " +
                    "@return String с номером ID удаленной сущности.",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(example = "Удален продукт по ID: 2")
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteProduct(
            @Parameter(
                    description = "Уникальный идентификатор продукта",
                    required = true,
                    example = "2"
            )
            @PathVariable("id") Long id);

    @Operation(
            summary = "Фильтрация продуктов по статусу",
            description = "..." +
                    "Параметры запроса: " +
                    "@param status - ProductStatus. " +
                    "@return List<ProductDto> в формате JSON.",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ с ProductDto",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "array", implementation = ProductDto.class)
                            )
                    )
            }
    )
    @GetMapping("/filter")
    ResponseEntity<List<ProductDto>> getFilterProductsByStatus(
            @Parameter(
                    description = "Статус продукта для фильтрации",
                    example = "SELLABLE",
                    schema = @Schema(implementation = ProductStatus.class)
            )
            @RequestParam("status") ProductStatus status);

    @Operation(
            summary = "Сумма значений по статуту",
            description = "..." +
                    "Параметры запроса: " +
                    "@param status - ProductStatus. " +
                    "@return String в формате JSON.",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ с суммой",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(example = "Сумма общей стоимости продуктов 555 со статусом: INBOUND")
                            )
                    )
            }
    )
    @GetMapping("/sum/status")
    ResponseEntity<String> getSumProductsByStatus(
            @Parameter(
                    description = "Сумма продукта для статуса.",
                    example = "INBOUND",
                    schema = @Schema(implementation = ProductStatus.class)
            )
            @RequestParam("status") ProductStatus status);

    @Operation(
            summary = "Сумма значений по статуту",
            description = """
                    ...
                    Параметры запроса:
                    @param ffc - ProductStatus.
                    @return String в формате JSON.
                    """,
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ с суммой",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(example = "Сумма общей стоимости продуктов 555 для fulfillmentCenter: fc1")
                            )
                    )
            }
    )
    @GetMapping("/sum/ffc")
    ResponseEntity<String> getSumValueByFulfillmentCenter(
            @Parameter(
                    description = "Сумма продукта для ffc.",
                    example = "fc1",
                    schema = @Schema(implementation = String.class)
            )
            @RequestParam("ffc") String fulfillmentCenter);
}

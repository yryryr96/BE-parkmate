package com.parkmate.parkmateorderservice.order.presentation;

import com.parkmate.parkmateorderservice.common.response.ApiResponse;
import com.parkmate.parkmateorderservice.order.application.OrderService;
import com.parkmate.parkmateorderservice.order.dto.request.OrderCreateRequestDto;
import com.parkmate.parkmateorderservice.order.vo.request.OrderCreateRequestVo;
import com.parkmate.parkmateorderservice.order.vo.response.OrderCreateResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private static final String USER_UUID_HEADER = "X-User-UUID";

    @Operation
    @PostMapping
    public ApiResponse<OrderCreateResponseVo> createOrder(@RequestHeader(USER_UUID_HEADER) String userUuid,
                                                          @RequestBody OrderCreateRequestVo orderCreateRequestVo) {

        return ApiResponse.created(
                orderService.create(OrderCreateRequestDto.of(userUuid, orderCreateRequestVo)).toVo()
        );
    }

}

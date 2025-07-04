package com.parkmate.parkmateorderservice.order.presentation;

import com.parkmate.parkmateorderservice.common.response.ApiResponse;
import com.parkmate.parkmateorderservice.common.response.CursorPage;
import com.parkmate.parkmateorderservice.order.application.OrderService;
import com.parkmate.parkmateorderservice.order.dto.request.OrderCreateRequestDto;
import com.parkmate.parkmateorderservice.order.dto.request.OrderGetRequestDto;
import com.parkmate.parkmateorderservice.order.dto.request.OrdersGetRequestDto;
import com.parkmate.parkmateorderservice.order.dto.response.OrderResponseDto;
import com.parkmate.parkmateorderservice.order.vo.response.OrderResponseVo;
import com.parkmate.parkmateorderservice.order.vo.request.OrderCreateRequestVo;
import com.parkmate.parkmateorderservice.order.vo.request.OrdersGetRequestVo;
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

    @Operation(
            summary = "주문 생성",
            description = """
                    사용자가 주문을 생성합니다. 주문 생성 시 X-User-UUID 헤더를 통해 사용자 UUID를 전달해야 합니다.
                    주문 생성 요청은 주문 정보와 결제 정보를 포함해야 합니다.
                    orderType은 `RESERVATION`, `POINT`가 있습니다.
                    paymentType은 `PG`, `POINT`가 있습니다.
                    """,
            tags = {"ORDER-SERVICE"}
    )
    @PostMapping
    public ApiResponse<OrderCreateResponseVo> createOrder(@RequestHeader(USER_UUID_HEADER) String userUuid,
                                                          @RequestBody OrderCreateRequestVo orderCreateRequestVo) {

        return ApiResponse.created(
                orderService.create(OrderCreateRequestDto.of(userUuid, orderCreateRequestVo)).toVo()
        );
    }

    @Operation(
            summary = "주문 확인",
            description = """
                    주문을 확인합니다. 주문 확인 시 X-User-UUID 헤더를 통해 사용자 UUID를 전달해야 합니다.
                    주문 코드를 통해 주문을 확인합니다.
                    """,
            tags = {"ORDER-SERVICE"}
    )
    @GetMapping("/{orderCode}")
    public ApiResponse<OrderResponseVo> getOrder(@RequestHeader(USER_UUID_HEADER) String userUuid,
                                                 @PathVariable String orderCode) {

        return ApiResponse.ok(
                "주문 내역 조회에 성공했습니다.",
                orderService.getOrder(OrderGetRequestDto.of(userUuid, orderCode)).toVo()
        );
    }

    @Operation(
            summary = "주문 내역 조회",
            description = """
                    사용자의 주문 내역을 조회합니다. X-User-UUID 헤더를 통해 사용자 UUID를 전달해야 합니다.
                    사용자의 모든 주문 내역을 반환합니다.
                    paymentType은 `PG`, `POINT`가 있습니다. 삽입하지 않으면 모든 결제 타입을 조회합니다.
                    orderType은 `RESERVATION`, `POINT`가 있습니다. 삽입하지 않으면 모든 주문 타입을 조회합니다.
                    size의 기본 값은 10 입니다.
                    """,
            tags = {"ORDER-SERVICE"}
    )
    @GetMapping
    public ApiResponse<CursorPage<OrderResponseVo>> getOrders(@RequestHeader(USER_UUID_HEADER) String userUuid,
                                                              @ModelAttribute OrdersGetRequestVo ordersGetRequestVo) {

        return ApiResponse.ok(
                "주문 내역 조회에 성공했습니다.",
                orderService.getOrders(OrdersGetRequestDto.from(userUuid, ordersGetRequestVo))
                        .map(OrderResponseDto::toVo)
        );
    }
}

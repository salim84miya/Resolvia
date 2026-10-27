INSERT INTO customer_order (
    id,
    customer_id,
    order_status,
    estimated_delivery_time,
    created_at
)
VALUES
    (1,101, 'SHIPPED', CURRENT_TIMESTAMP + INTERVAL '3' day, CURRENT_TIMESTAMP),
    (2,101, 'PLACED', CURRENT_TIMESTAMP + INTERVAL '5' day, CURRENT_TIMESTAMP),
    (3,102, 'OUT_FOR_DELIVERY', CURRENT_TIMESTAMP + INTERVAL '1' day, CURRENT_TIMESTAMP),
    (4,103, 'DELIVERED', CURRENT_TIMESTAMP - INTERVAL '2' day, CURRENT_TIMESTAMP),
    (5,102, 'CANCELLED', NULL, CURRENT_TIMESTAMP);


INSERT INTO support_ticket_table(
    ticket_id,
    customer_id,
    order_id,
    ticket_status,
    ticket_priority,
    title,
    description,
    created_at,
    updated_at
)
VALUES
     (
            1,
            101,
             1,
            'OPEN',
             'HIGH',
            'Damaged Product Received',
            'Customer reported that the product arrived damaged and requested a replacement.',
            CURRENT_TIMESTAMP - INTERVAL '2' DAY,
            CURRENT_TIMESTAMP - INTERVAL '2' DAY
        ),
        (
            2, 101, 2,
            'IN_PROGRESS', 'MEDIUM',
            'Order Delivery Delayed',
            'Customer reported that the order has not arrived within the expected delivery timeframe.',
            CURRENT_TIMESTAMP - INTERVAL '1' DAY,
            CURRENT_TIMESTAMP
        ),
        (
            3, 102, 3,
            'OPEN', 'HIGH',
            'Wrong Item Delivered',
            'Customer reported receiving an item different from the one ordered.',
            CURRENT_TIMESTAMP - INTERVAL '3' HOUR,
            CURRENT_TIMESTAMP - INTERVAL '3' HOUR
        ),
        (
            4, 103, 4,
            'RESOLVED', 'LOW',
            'Product Information Request',
            'Customer requested additional information about the delivered product.',
            CURRENT_TIMESTAMP - INTERVAL '5' DAY,
            CURRENT_TIMESTAMP - INTERVAL '2' DAY
        ),
        (
            5, 102, 5,
            'CLOSED', 'LOW',
            'Cancelled Order Inquiry',
            'Customer requested clarification regarding the cancellation of the order.',
            CURRENT_TIMESTAMP - INTERVAL '4' DAY,
            CURRENT_TIMESTAMP - INTERVAL '3' DAY
        );
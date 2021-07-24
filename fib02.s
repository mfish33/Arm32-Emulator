main:
        mov     r0, #11
        bl       fib
        END
fib:
        push    {r4, r5, r11, lr}
        mov     r4, r0
        mov     r5, #0
        cmp     r0, #2
        bge     .LBB1_2
        mov     r0, r4
        b       .LBB1_4
.LBB1_2:
        mov     r5, #0
.LBB1_3:
        sub     r0, r4, #1
        bl      fib
        add     r5, r0, r5
        sub     r0, r4, #2
        cmp     r4, #3
        mov     r4, r0
        bgt     .LBB1_3
.LBB1_4:
        add     r0, r0, r5
        pop     {r4, r5, r11, lr}
        bx      lr
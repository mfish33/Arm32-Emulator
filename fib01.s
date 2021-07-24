main:
        push    {r11, lr}
        mov     r0, #11
        bl      fib
        pop     {r11, lr}
        END
fib:
        push    {r4, r5, r11, lr}
        mov     r4, r0
        cmp     r0, #2
        blt     .LBB1_2
        sub     r0, r4, #1
        bl      fib
        mov     r5, r0
        sub     r0, r4, #2
        bl      fib
        add     r4, r0, r5
.LBB1_2:
        mov     r0, r4
        pop     {r4, r5, r11, lr}
        bx      lr
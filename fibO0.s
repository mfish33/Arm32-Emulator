main:
        mov     r0, #11
        bl      fib
        END
fib:
        push    {r11, lr}
        mov     r11, sp
        sub     sp, sp, #16
        str     r0, [sp, #8]
        ldr     r0, [sp, #8]
        cmp     r0, #1
        bgt     .LBB1_2
        b       .LBB1_1
.LBB1_1:
        ldr     r0, [sp, #8]
        str     r0, [r11, #-4]
        b       .LBB1_3
.LBB1_2:
        ldr     r0, [sp, #8]
        sub     r0, r0, #1
        bl      fib
        ldr     r1, [sp, #8]
        sub     r1, r1, #2
        str     r0, [sp, #4]
        mov     r0, r1
        bl      fib
        ldr     r1, [sp, #4]
        add     r0, r1, r0
        str     r0, [r11, #-4]
        b       .LBB1_3
.LBB1_3:
        ldr     r0, [r11, #-4]
        mov     sp, r11
        pop     {r11, lr}
        bx      lr
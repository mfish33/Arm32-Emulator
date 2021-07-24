main:
        push    {r4, r5, r11, lr}
        add     r11, sp, #8
        sub     sp, sp, #72
        sub     sp, sp, #1024
        mov     r0, #0
        str     r0, [r11, #-12]
        str     r0, [sp, #8]
        b       .LBB5_1
.LBB5_1:
        ldr     r0, [sp, #8]
        cmp     r0, #39
        bgt     .LBB5_4
        b       .LBB5_2
.LBB5_2:
        ldr     r0, [sp, #8]
        add     r1, sp, #12
        add     r0, r1, r0, lsl #2
        mov     r1, #1
        str     r1, [r0, #920]
        b       .LBB5_3
.LBB5_3:
        ldr     r0, [sp, #8]
        add     r0, r0, #1
        str     r0, [sp, #8]
        b       .LBB5_1
.LBB5_4:
        add     r0, sp, #12
        mov     r1, #0
        bl      SolveGame
        str     r0, [sp, #4]
        ldr     r0, [sp, #4]
        sub     sp, r11, #8
        pop     {r4, r5, r11, lr}
        END
initGame:
        push    {r11, lr}
        mov     r11, sp
        sub     sp, sp, #16
        str     r0, [r11, #-4]
        str     r1, [sp, #8]
        mov     r0, #0
        str     r0, [sp, #4]
        b       .LBB0_1
.LBB0_1:
        ldr     r0, [sp, #4]
        cmp     r0, #39
        bgt     .LBB0_8
        b       .LBB0_2
.LBB0_2:
        mov     r0, #0
        str     r0, [sp]
        b       .LBB0_3
.LBB0_3:
        ldr     r0, [sp]
        cmp     r0, #20
        bgt     .LBB0_6
        b       .LBB0_4
.LBB0_4:
        ldr     r0, [r11, #-4]
        ldr     r1, [sp, #4]
        mov     r2, #21
        mla     r3, r1, r2, r0
        ldr     r0, [sp]
        ldrb    r3, [r3, r0]
        ldr     r12, [sp, #8]
        mla     lr, r1, r2, r12
        strb    r3, [lr, r0]
        b       .LBB0_5
.LBB0_5:
        ldr     r0, [sp]
        add     r0, r0, #1
        str     r0, [sp]
        b       .LBB0_3
.LBB0_6:
        b       .LBB0_7
.LBB0_7:
        ldr     r0, [sp, #4]
        add     r0, r0, #1
        str     r0, [sp, #4]
        b       .LBB0_1
.LBB0_8:
        mov     r0, #0
        str     r0, [sp, #4]
        b       .LBB0_9
.LBB0_9:
        ldr     r0, [sp, #4]
        cmp     r0, #19
        bgt     .LBB0_12
        b       .LBB0_10
.LBB0_10:
        ldr     r0, [r11, #-4]
        ldr     r1, [sp, #4]
        add     r0, r0, r1, lsl #2
        ldr     r0, [r0, #840]
        ldr     r2, [sp, #8]
        add     r1, r2, r1, lsl #2
        str     r0, [r1, #840]
        b       .LBB0_11
.LBB0_11:
        ldr     r0, [sp, #4]
        add     r0, r0, #1
        str     r0, [sp, #4]
        b       .LBB0_9
.LBB0_12:
        mov     r0, #0
        str     r0, [sp, #4]
        b       .LBB0_13
.LBB0_13:
        ldr     r0, [sp, #4]
        cmp     r0, #39
        bgt     .LBB0_16
        b       .LBB0_14
.LBB0_14:
        ldr     r0, [r11, #-4]
        ldr     r1, [sp, #4]
        add     r0, r0, r1, lsl #2
        ldr     r0, [r0, #920]
        ldr     r2, [sp, #8]
        add     r1, r2, r1, lsl #2
        str     r0, [r1, #920]
        b       .LBB0_15
.LBB0_15:
        ldr     r0, [sp, #4]
        add     r0, r0, #1
        str     r0, [sp, #4]
        b       .LBB0_13
.LBB0_16:
        mov     sp, r11
        pop     {r11, lr}
        bx      lr
my_strncmp:
        sub     sp, sp, #20
        str     r0, [sp, #16]
        str     r1, [sp, #12]
        str     r2, [sp, #8]
        mov     r0, #0
        str     r0, [sp]
        str     r0, [sp, #4]
        b       .LBB1_1
.LBB1_1:
        ldr     r0, [sp, #4]
        ldr     r1, [sp, #8]
        cmp     r0, r1
        bge     .LBB1_9
        b       .LBB1_2
.LBB1_2:
        ldr     r0, [sp, #16]
        ldr     r1, [sp, #4]
        ldrb    r0, [r0, r1]
        ldr     r2, [sp, #12]
        ldrb    r1, [r2, r1]
        cmp     r0, r1
        bge     .LBB1_4
        b       .LBB1_3
.LBB1_3:
        mvn     r0, #0
        str     r0, [sp]
        b       .LBB1_9
.LBB1_4:
        ldr     r0, [sp, #16]
        ldr     r1, [sp, #4]
        ldrb    r0, [r0, r1]
        ldr     r2, [sp, #12]
        ldrb    r1, [r2, r1]
        cmp     r0, r1
        ble     .LBB1_6
        b       .LBB1_5
.LBB1_5:
        mov     r0, #1
        str     r0, [sp]
        b       .LBB1_9
.LBB1_6:
        b       .LBB1_7
.LBB1_7:
        b       .LBB1_8
.LBB1_8:
        ldr     r0, [sp, #4]
        add     r0, r0, #1
        str     r0, [sp, #4]
        b       .LBB1_1
.LBB1_9:
        ldr     r0, [sp]
        add     sp, sp, #20
        bx      lr
my_strcpy:
        sub     sp, sp, #12
        str     r0, [sp, #8]
        str     r1, [sp, #4]
        mov     r0, #0
        str     r0, [sp]
        b       .LBB2_1
.LBB2_1:
        ldr     r0, [sp, #4]
        ldr     r1, [sp]
        ldrb    r0, [r0, r1]
        ldr     r2, [sp, #8]
        strb    r0, [r2, r1]
        ldr     r0, [sp]
        add     r0, r0, #1
        str     r0, [sp]
        b       .LBB2_2
.LBB2_2:
        ldr     r0, [sp, #4]
        ldr     r1, [sp]
        ldrb    r0, [r0, r1]
        cmp     r0, #0
        bne     .LBB2_1
        b       .LBB2_3
.LBB2_3:
        add     sp, sp, #12
        bx      lr
CheckConstraint:
        push    {r4, r5, r11, lr}
        add     r11, sp, #8
        sub     sp, sp, #584
        str     r0, [r11, #-16]
        str     r1, [r11, #-20]
        mov     r0, #0
        str     r0, [sp, #168]
        b       .LBB3_1
.LBB3_1:
        ldr     r0, [sp, #168]
        cmp     r0, #19
        bgt     .LBB3_8
        b       .LBB3_2
.LBB3_2:
        mov     r0, #0
        str     r0, [sp, #164]
        b       .LBB3_3
.LBB3_3:
        ldr     r0, [sp, #164]
        cmp     r0, #19
        bgt     .LBB3_6
        b       .LBB3_4
.LBB3_4:
        ldr     r0, [r11, #-16]
        ldr     r1, [sp, #164]
        mov     r2, #21
        mla     r3, r1, r2, r0
        ldr     r0, [sp, #168]
        ldrb    r2, [r3, r0]
        add     r0, r0, r0, lsl #2
        add     r3, sp, #172
        add     r0, r3, r0, lsl #2
        strb    r2, [r0, r1]
        b       .LBB3_5
.LBB3_5:
        ldr     r0, [sp, #164]
        add     r0, r0, #1
        str     r0, [sp, #164]
        b       .LBB3_3
.LBB3_6:
        b       .LBB3_7
.LBB3_7:
        ldr     r0, [sp, #168]
        add     r0, r0, #1
        str     r0, [sp, #168]
        b       .LBB3_1
.LBB3_8:
        mov     r0, #0
        str     r0, [sp, #168]
        b       .LBB3_9
.LBB3_9:
        ldr     r0, [sp, #168]
        cmp     r0, #39
        bgt     .LBB3_12
        b       .LBB3_10
.LBB3_10:
        ldr     r0, [r11, #-16]
        ldr     r1, [sp, #168]
        add     r0, r0, r1, lsl #2
        ldr     r0, [r0, #920]
        add     r2, sp, #4
        str     r0, [r2, r1, lsl #2]
        b       .LBB3_11
.LBB3_11:
        ldr     r0, [sp, #168]
        add     r0, r0, #1
        str     r0, [sp, #168]
        b       .LBB3_9
.LBB3_12:
        mov     r0, #0
        str     r0, [sp, #168]
        b       .LBB3_13
.LBB3_13:
        ldr     r0, [sp, #168]
        cmp     r0, #19
        bgt     .LBB3_26
        b       .LBB3_14
.LBB3_14:
        mov     r0, #0
        str     r0, [sp, #164]
        b       .LBB3_15
.LBB3_15:
        ldr     r0, [sp, #164]
        cmp     r0, #39
        bgt     .LBB3_22
        b       .LBB3_16
.LBB3_16:
        ldr     r0, [sp, #164]
        add     r1, sp, #4
        ldr     r0, [r1, r0, lsl #2]
        cmp     r0, #0
        bne     .LBB3_18
        b       .LBB3_17
.LBB3_17:
        b       .LBB3_21
.LBB3_18:
        ldr     r0, [sp, #164]
        ldr     r1, .LCPI3_0
        mov     r2, #21
        mla     r3, r0, r2, r1
        ldr     r0, [sp, #168]
        add     r0, r0, r0, lsl #2
        add     r1, sp, #172
        add     r1, r1, r0, lsl #2
        ldr     r0, [r11, #-20]
        add     r2, r0, #1
        mov     r0, r3
        bl      my_strncmp
        cmp     r0, #0
        bne     .LBB3_20
        b       .LBB3_19
.LBB3_19:
        ldr     r0, [sp, #164]
        add     r1, sp, #4
        mov     r2, #0
        str     r2, [r1, r0, lsl #2]
        b       .LBB3_22
.LBB3_20:
        b       .LBB3_21
.LBB3_21:
        ldr     r0, [sp, #164]
        add     r0, r0, #1
        str     r0, [sp, #164]
        b       .LBB3_15
.LBB3_22:
        ldr     r0, [sp, #164]
        cmp     r0, #40
        blt     .LBB3_24
        b       .LBB3_23
.LBB3_23:
        mov     r0, #0
        str     r0, [r11, #-12]
        b       .LBB3_27
.LBB3_24:
        b       .LBB3_25
.LBB3_25:
        ldr     r0, [sp, #168]
        add     r0, r0, #1
        str     r0, [sp, #168]
        b       .LBB3_13
.LBB3_26:
        mov     r0, #1
        str     r0, [r11, #-12]
        b       .LBB3_27
.LBB3_27:
        ldr     r0, [r11, #-12]
        sub     sp, r11, #8
        pop     {r4, r5, r11, lr}
        bx      lr
.LCPI3_0:
        .long   g_mainSetting
SolveGame:
        push    {r4, r5, r11, lr}
        add     r11, sp, #8
        sub     sp, sp, #1104
        str     r0, [r11, #-16]
        str     r1, [r11, #-20]
        ldr     r0, [r11, #-20]
        cmp     r0, #20
        bne     .LBB4_2
        b       .LBB4_1
.LBB4_1:
        mov     r0, #1
        str     r0, [r11, #-12]
        b       .LBB4_13
.LBB4_2:
        mov     r0, #0
        str     r0, [r11, #-24]
        b       .LBB4_3
.LBB4_3:
        ldr     r0, [r11, #-24]
        cmp     r0, #39
        bgt     .LBB4_12
        b       .LBB4_4
.LBB4_4:
        ldr     r0, [r11, #-16]
        ldr     r1, [r11, #-24]
        add     r0, r0, r1, lsl #2
        ldr     r0, [r0, #920]
        cmp     r0, #0
        beq     .LBB4_10
        b       .LBB4_5
.LBB4_5:
        ldr     r0, [r11, #-16]
        add     r1, sp, #4
        str     r1, [sp]
        bl      initGame
        ldr     r0, [r11, #-24]
        ldr     r1, [r11, #-20]
        ldr     r2, [sp]
        add     r1, r2, r1, lsl #2
        str     r0, [r1, #840]
        ldr     r0, [r11, #-24]
        add     r0, r2, r0, lsl #2
        mov     r1, #0
        str     r1, [r0, #920]
        ldr     r0, [r11, #-20]
        mov     r1, #21
        mla     r3, r0, r1, r2
        ldr     r0, [r11, #-24]
        ldr     r12, .LCPI4_0
        mla     lr, r0, r1, r12
        mov     r0, r3
        mov     r1, lr
        bl      my_strcpy
        ldr     r1, [r11, #-20]
        ldr     r0, [sp]
        bl      CheckConstraint
        cmp     r0, #0
        bne     .LBB4_7
        b       .LBB4_6
.LBB4_6:
        b       .LBB4_11
.LBB4_7:
        ldr     r0, [r11, #-20]
        add     r1, r0, #1
        add     r0, sp, #4
        bl      SolveGame
        cmp     r0, #0
        beq     .LBB4_9
        b       .LBB4_8
.LBB4_8:
        mov     r0, #1
        str     r0, [r11, #-12]
        b       .LBB4_13
.LBB4_9:
        b       .LBB4_10
.LBB4_10:
        b       .LBB4_11
.LBB4_11:
        ldr     r0, [r11, #-24]
        add     r0, r0, #1
        str     r0, [r11, #-24]
        b       .LBB4_3
.LBB4_12:
        mov     r0, #0
        str     r0, [r11, #-12]
        b       .LBB4_13
.LBB4_13:
        ldr     r0, [r11, #-12]
        sub     sp, r11, #8
        pop     {r4, r5, r11, lr}
        bx      lr
.LCPI4_0:
        .long   g_mainSetting
g_mainSetting:
        .asciz  "                   *"
        .asciz  "                  **"
        .asciz  "       *     *  *   "
        .asciz  "           *   ***  "
        .asciz  "*   *          *  * "
        .asciz  "           **    * *"
        .asciz  "  * **  *     *     "
        .asciz  "            *****   "
        .asciz  "   **    *   * *    "
        .asciz  "          * *   *** "
        .asciz  " * *   *   *  *     "
        .asciz  "      **    * *  *  "
        .asciz  "  *  *  *     * *   "
        .asciz  "          *  *  ** *"
        .asciz  " ***   *         *  "
        .asciz  " *  * * *   * *     "
        .asciz  "   **  * *    * *   "
        .asciz  "  *****           * "
        .asciz  "  *  *   * **  *    "
        .asciz  "    *  *  * * * *   "
        .asciz  "         * ***   ** "
        .asciz  "     ** *  *   * *  "
        .asciz  "        *** * *   * "
        .asciz  "  ***   *  *  *     "
        .asciz  "       *  ** * *  * "
        .asciz  "   ** * * *  * *    "
        .asciz  "    *  *  * *** *   "
        .asciz  "*****         *    *"
        .asciz  "   **    * * ***    "
        .asciz  "*  * * ***    *     "
        .asciz  "         ***  ** * *"
        .asciz  "     ** *   ** * *  "
        .asciz  "***   * *      ***  "
        .asciz  "      **   ** *** * "
        .asciz  " * * **  *** *      "
        .asciz  "* *** ***   **      "
        .asciz  "        *** * ** ***"
        .asciz  " **** **       ***  "
        .asciz  "**   *** *   **  ** "
        .asciz  "*   * * *****  **  *"
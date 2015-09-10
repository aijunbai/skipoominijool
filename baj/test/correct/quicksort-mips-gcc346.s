	.file	1 "quicksort.c"
	.section .mdebug.abi32
	.previous
	.abicalls
	.globl	i
	.data
	.align	2
	.type	i, @object
	.size	i, 4
i:
	.word	1
	.globl	data
	.align	2
	.type	data, @object
	.size	data, 40
data:
	.word	123
	.word	52
	.word	8
	.word	74
	.word	62
	.word	74
	.word	55
	.word	44
	.word	74
	.word	80
	.rdata
	.align	2
$LC0:
	.ascii	"\n"
	.ascii	"    Member \000"
	.align	2
$LC1:
	.ascii	"%s\000"
	.align	2
$LC2:
	.ascii	"%d\000"
	.align	2
$LC3:
	.ascii	" is \000"
	.text
	.align	2
	.globl	printArray
	.ent	printArray
	.type	printArray, @function
printArray:
	.frame	$fp,40,$31		# vars= 8, regs= 2/0, args= 16, gp= 8
	.mask	0xc0000000,-4
	.fmask	0x00000000,0
	.set	noreorder
	.cpload	$25
	.set	reorder
	addiu	$sp,$sp,-40
	sw	$31,36($sp)
	sw	$fp,32($sp)
	move	$fp,$sp
	.cprestore	16
	sw	$4,40($fp)
	sw	$0,24($fp)
	la	$2,$LC0
	sw	$2,28($fp)
$L2:
	lw	$2,24($fp)
	slt	$2,$2,10
	beq	$2,$0,$L1
	la	$4,$LC1
	lw	$5,28($fp)
	jal	printf
	la	$4,$LC2
	lw	$5,24($fp)
	jal	printf
	la	$4,$LC3
	jal	printf
	lw	$2,24($fp)
	sll	$3,$2,2
	lw	$2,40($fp)
	addu	$2,$3,$2
	la	$4,$LC2
	lw	$5,0($2)
	jal	printf
	lw	$2,24($fp)
	addiu	$2,$2,1
	sw	$2,24($fp)
	b	$L2
$L1:
	move	$sp,$fp
	lw	$31,36($sp)
	lw	$fp,32($sp)
	addiu	$sp,$sp,40
	j	$31
	.end	printArray
	.align	2
	.globl	partition
	.ent	partition
	.type	partition, @function
partition:
	.frame	$fp,32,$31		# vars= 16, regs= 1/0, args= 0, gp= 8
	.mask	0x40000000,-8
	.fmask	0x00000000,0
	.set	noreorder
	.cpload	$25
	.set	reorder
	addiu	$sp,$sp,-32
	sw	$fp,24($sp)
	move	$fp,$sp
	sw	$4,32($fp)
	sw	$5,36($fp)
	sw	$6,40($fp)
	lw	$2,36($fp)
	sll	$3,$2,2
	lw	$2,32($fp)
	addu	$2,$3,$2
	lw	$2,0($2)
	sw	$2,8($fp)
	lw	$2,36($fp)
	sw	$2,12($fp)
	lw	$2,40($fp)
	sw	$2,16($fp)
$L5:
	lw	$2,12($fp)
	lw	$3,16($fp)
	slt	$2,$2,$3
	beq	$2,$0,$L6
$L7:
	lw	$2,12($fp)
	lw	$3,16($fp)
	slt	$2,$2,$3
	beq	$2,$0,$L8
	lw	$2,16($fp)
	sll	$3,$2,2
	lw	$2,32($fp)
	addu	$2,$3,$2
	lw	$3,0($2)
	lw	$2,8($fp)
	slt	$2,$3,$2
	bne	$2,$0,$L8
	lw	$2,16($fp)
	addiu	$2,$2,-1
	sw	$2,16($fp)
	b	$L7
$L8:
	lw	$2,12($fp)
	sll	$3,$2,2
	lw	$2,32($fp)
	addu	$4,$3,$2
	lw	$2,16($fp)
	sll	$3,$2,2
	lw	$2,32($fp)
	addu	$2,$3,$2
	lw	$2,0($2)
	sw	$2,0($4)
$L9:
	lw	$2,12($fp)
	lw	$3,16($fp)
	slt	$2,$2,$3
	beq	$2,$0,$L10
	lw	$2,12($fp)
	sll	$3,$2,2
	lw	$2,32($fp)
	addu	$2,$3,$2
	lw	$3,0($2)
	lw	$2,8($fp)
	slt	$2,$2,$3
	bne	$2,$0,$L10
	lw	$2,12($fp)
	addiu	$2,$2,1
	sw	$2,12($fp)
	b	$L9
$L10:
	lw	$2,16($fp)
	sll	$3,$2,2
	lw	$2,32($fp)
	addu	$4,$3,$2
	lw	$2,12($fp)
	sll	$3,$2,2
	lw	$2,32($fp)
	addu	$2,$3,$2
	lw	$2,0($2)
	sw	$2,0($4)
	b	$L5
$L6:
	lw	$2,12($fp)
	sll	$3,$2,2
	lw	$2,32($fp)
	addu	$3,$3,$2
	lw	$2,8($fp)
	sw	$2,0($3)
	lw	$2,12($fp)
	move	$sp,$fp
	lw	$fp,24($sp)
	addiu	$sp,$sp,32
	j	$31
	.end	partition
	.rdata
	.align	2
$LC4:
	.ascii	"Please input 10 integers to be sorted.\n\000"
	.text
	.align	2
	.globl	readArray
	.ent	readArray
	.type	readArray, @function
readArray:
	.frame	$fp,40,$31		# vars= 8, regs= 2/0, args= 16, gp= 8
	.mask	0xc0000000,-4
	.fmask	0x00000000,0
	.set	noreorder
	.cpload	$25
	.set	reorder
	addiu	$sp,$sp,-40
	sw	$31,36($sp)
	sw	$fp,32($sp)
	move	$fp,$sp
	.cprestore	16
	sw	$0,24($fp)
	la	$4,$LC4
	jal	printf
$L12:
	lw	$2,24($fp)
	slt	$2,$2,10
	beq	$2,$0,$L11
	lw	$2,24($fp)
	sll	$3,$2,2
	la	$2,data
	addu	$2,$3,$2
	la	$4,$LC2
	move	$5,$2
	jal	scanf
	lw	$2,24($fp)
	addiu	$2,$2,1
	sw	$2,24($fp)
	b	$L12
$L11:
	move	$sp,$fp
	lw	$31,36($sp)
	lw	$fp,32($sp)
	addiu	$sp,$sp,40
	j	$31
	.end	readArray
	.align	2
	.globl	qsort
	.ent	qsort
	.type	qsort, @function
qsort:
	.frame	$fp,40,$31		# vars= 8, regs= 2/0, args= 16, gp= 8
	.mask	0xc0000000,-4
	.fmask	0x00000000,0
	.set	noreorder
	.cpload	$25
	.set	reorder
	addiu	$sp,$sp,-40
	sw	$31,36($sp)
	sw	$fp,32($sp)
	move	$fp,$sp
	.cprestore	16
	sw	$4,40($fp)
	sw	$5,44($fp)
	sw	$6,48($fp)
	lw	$2,44($fp)
	lw	$3,48($fp)
	slt	$2,$2,$3
	bne	$2,$0,$L15
	b	$L14
$L15:
	lw	$4,40($fp)
	lw	$5,44($fp)
	lw	$6,48($fp)
	jal	partition
	sw	$2,24($fp)
	lw	$2,24($fp)
	addiu	$2,$2,-1
	lw	$4,40($fp)
	lw	$5,44($fp)
	move	$6,$2
	jal	qsort
	lw	$2,24($fp)
	addiu	$2,$2,1
	lw	$4,40($fp)
	move	$5,$2
	lw	$6,48($fp)
	jal	qsort
$L14:
	move	$sp,$fp
	lw	$31,36($sp)
	lw	$fp,32($sp)
	addiu	$sp,$sp,40
	j	$31
	.end	qsort
	.rdata
	.align	2
$LC5:
	.ascii	"\n"
	.ascii	"Continue? 1-yes, 0-no: \000"
	.align	2
$LC6:
	.ascii	"\n"
	.ascii	"Executing No.\000"
	.align	2
$LC7:
	.ascii	" quicksort:\n"
	.ascii	"  Before sorting:\000"
	.align	2
$LC8:
	.ascii	"\n"
	.ascii	"  After sorting:\000"
	.text
	.align	2
	.globl	main
	.ent	main
	.type	main, @function
main:
	.frame	$fp,40,$31		# vars= 8, regs= 2/0, args= 16, gp= 8
	.mask	0xc0000000,-4
	.fmask	0x00000000,0
	.set	noreorder
	.cpload	$25
	.set	reorder
	addiu	$sp,$sp,-40
	sw	$31,36($sp)
	sw	$fp,32($sp)
	move	$fp,$sp
	.cprestore	16
$L17:
	la	$2,i
	lw	$2,0($2)
	slt	$2,$2,2
	bne	$2,$0,$L19
	la	$4,$LC5
	jal	printf
	la	$4,$LC2
	addiu	$5,$fp,24
	jal	scanf
	lw	$2,24($fp)
	bne	$2,$0,$L20
	b	$L16
$L20:
	lw	$3,24($fp)
	li	$2,1			# 0x1
	beq	$3,$2,$L21
	b	$L17
$L21:
	jal	readArray
$L19:
	la	$4,$LC6
	jal	printf
	la	$2,i
	la	$4,$LC2
	lw	$5,0($2)
	jal	printf
	la	$4,$LC7
	jal	printf
	la	$4,data
	jal	printArray
	la	$4,data
	move	$5,$0
	li	$6,9			# 0x9
	jal	qsort
	la	$4,$LC8
	jal	printf
	la	$4,data
	jal	printArray
	la	$3,i
	la	$2,i
	lw	$2,0($2)
	addiu	$2,$2,1
	sw	$2,0($3)
	b	$L17
$L16:
	move	$sp,$fp
	lw	$31,36($sp)
	lw	$fp,32($sp)
	addiu	$sp,$sp,40
	j	$31
	.end	main
	.ident	"GCC: (GNU) 3.4.6 ()"

	.file	"quicksort.c"
.globl i
	.data
	.align 4
	.type	i, @object
	.size	i, 4
i:
	.long	1
.globl data
	.align 32
	.type	data, @object
	.size	data, 40
data:
	.long	123
	.long	52
	.long	8
	.long	74
	.long	62
	.long	74
	.long	55
	.long	44
	.long	74
	.long	80
	.section	.rodata
.LC0:
	.string	"\n    Member "
.LC1:
	.string	"%s"
.LC2:
	.string	"%d"
.LC3:
	.string	" is "
	.text
.globl printArray
	.type	printArray, @function
printArray:
	pushl	%ebp
	movl	%esp, %ebp
	subl	$24, %esp
	movl	$0, -8(%ebp)
	movl	$.LC0, -4(%ebp)
	jmp	.L2
.L3:
	movl	-4(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	$.LC1, (%esp)
	call	printf
	movl	-8(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	$.LC2, (%esp)
	call	printf
	movl	$.LC3, (%esp)
	call	printf
	movl	-8(%ebp), %eax
	sall	$2, %eax
	addl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	%eax, 4(%esp)
	movl	$.LC2, (%esp)
	call	printf
	addl	$1, -8(%ebp)
.L2:
	cmpl	$9, -8(%ebp)
	jle	.L3
	leave
	ret
	.size	printArray, .-printArray
.globl partition
	.type	partition, @function
partition:
	pushl	%ebp
	movl	%esp, %ebp
	subl	$16, %esp
	movl	12(%ebp), %eax
	sall	$2, %eax
	addl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	%eax, -12(%ebp)
	movl	12(%ebp), %eax
	movl	%eax, -8(%ebp)
	movl	16(%ebp), %eax
	movl	%eax, -4(%ebp)
	jmp	.L7
.L10:
	subl	$1, -4(%ebp)
.L9:
	movl	-8(%ebp), %eax
	cmpl	-4(%ebp), %eax
	jge	.L11
	movl	-4(%ebp), %eax
	sall	$2, %eax
	addl	8(%ebp), %eax
	movl	(%eax), %eax
	cmpl	-12(%ebp), %eax
	jge	.L10
.L11:
	movl	-8(%ebp), %eax
	sall	$2, %eax
	movl	%eax, %edx
	addl	8(%ebp), %edx
	movl	-4(%ebp), %eax
	sall	$2, %eax
	addl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	%eax, (%edx)
	jmp	.L13
.L14:
	addl	$1, -8(%ebp)
.L13:
	movl	-8(%ebp), %eax
	cmpl	-4(%ebp), %eax
	jge	.L15
	movl	-8(%ebp), %eax
	sall	$2, %eax
	addl	8(%ebp), %eax
	movl	(%eax), %eax
	cmpl	-12(%ebp), %eax
	jle	.L14
.L15:
	movl	-4(%ebp), %eax
	sall	$2, %eax
	movl	%eax, %edx
	addl	8(%ebp), %edx
	movl	-8(%ebp), %eax
	sall	$2, %eax
	addl	8(%ebp), %eax
	movl	(%eax), %eax
	movl	%eax, (%edx)
.L7:
	movl	-8(%ebp), %eax
	cmpl	-4(%ebp), %eax
	jl	.L9
	movl	-8(%ebp), %eax
	sall	$2, %eax
	movl	%eax, %edx
	addl	8(%ebp), %edx
	movl	-12(%ebp), %eax
	movl	%eax, (%edx)
	movl	-8(%ebp), %eax
	leave
	ret
	.size	partition, .-partition
	.section	.rodata
	.align 4
.LC4:
	.string	"Please input 10 integers to be sorted."
	.text
.globl readArray
	.type	readArray, @function
readArray:
	pushl	%ebp
	movl	%esp, %ebp
	subl	$24, %esp
	movl	$0, -4(%ebp)
	movl	$.LC4, (%esp)
	call	puts
	jmp	.L20
.L21:
	movl	-4(%ebp), %eax
	sall	$2, %eax
	addl	$data, %eax
	movl	%eax, 4(%esp)
	movl	$.LC2, (%esp)
	call	scanf
	addl	$1, -4(%ebp)
.L20:
	cmpl	$9, -4(%ebp)
	jle	.L21
	leave
	ret
	.size	readArray, .-readArray
.globl qsort
	.type	qsort, @function
qsort:
	pushl	%ebp
	movl	%esp, %ebp
	subl	$28, %esp
	movl	12(%ebp), %eax
	cmpl	16(%ebp), %eax
	jge	.L27
	movl	16(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	partition
	movl	%eax, -4(%ebp)
	movl	-4(%ebp), %eax
	subl	$1, %eax
	movl	%eax, 8(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	qsort
	movl	-4(%ebp), %edx
	addl	$1, %edx
	movl	16(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	%edx, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	qsort
.L27:
	leave
	ret
	.size	qsort, .-qsort
	.section	.rodata
.LC5:
	.string	"\nContinue? 1-yes, 0-no: "
.LC6:
	.string	"\nExecuting No."
.LC7:
	.string	" quicksort:\n  Before sorting:"
.LC8:
	.string	"\n  After sorting:"
	.text
.globl main
	.type	main, @function
main:
	leal	4(%esp), %ecx
	andl	$-16, %esp
	pushl	-4(%ecx)
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%ecx
	subl	$36, %esp
.L29:
	movl	i, %eax
	cmpl	$1, %eax
	jle	.L30
	movl	$.LC5, (%esp)
	call	printf
	leal	-8(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	$.LC2, (%esp)
	call	scanf
	movl	-8(%ebp), %eax
	testl	%eax, %eax
	je	.L36
	movl	-8(%ebp), %eax
	cmpl	$1, %eax
	jne	.L29
	call	readArray
.L30:
	movl	$.LC6, (%esp)
	call	printf
	movl	i, %eax
	movl	%eax, 4(%esp)
	movl	$.LC2, (%esp)
	call	printf
	movl	$.LC7, (%esp)
	call	printf
	movl	$data, (%esp)
	call	printArray
	movl	$9, 8(%esp)
	movl	$0, 4(%esp)
	movl	$data, (%esp)
	call	qsort
	movl	$.LC8, (%esp)
	call	printf
	movl	$data, (%esp)
	call	printArray
	movl	i, %eax
	addl	$1, %eax
	movl	%eax, i
	jmp	.L29
.L36:
	addl	$36, %esp
	popl	%ecx
	popl	%ebp
	leal	-4(%ecx), %esp
	ret
	.size	main, .-main
	.ident	"GCC: (GNU) 4.2.3 (Debian 4.2.3-5)"
	.section	.note.GNU-stack,"",@progbits

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
	movl	$0, -4(%ebp)
	movl	$.LC0, -8(%ebp)
.L2:
	cmpl	$9, -4(%ebp)
	jg	.L1
	movl	-8(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	$.LC1, (%esp)
	call	printf
	movl	-4(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	$.LC2, (%esp)
	call	printf
	movl	$.LC3, (%esp)
	call	printf
	movl	-4(%ebp), %eax
	leal	0(,%eax,4), %edx
	movl	8(%ebp), %eax
	movl	(%edx,%eax), %eax
	movl	%eax, 4(%esp)
	movl	$.LC2, (%esp)
	call	printf
	leal	-4(%ebp), %eax
	incl	(%eax)
	jmp	.L2
.L1:
	leave
	ret
	.size	printArray, .-printArray
.globl partition
	.type	partition, @function
partition:
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%ebx
	subl	$12, %esp
	movl	12(%ebp), %eax
	leal	0(,%eax,4), %edx
	movl	8(%ebp), %eax
	movl	(%edx,%eax), %eax
	movl	%eax, -8(%ebp)
	movl	12(%ebp), %eax
	movl	%eax, -12(%ebp)
	movl	16(%ebp), %eax
	movl	%eax, -16(%ebp)
.L5:
	movl	-12(%ebp), %eax
	cmpl	-16(%ebp), %eax
	jge	.L6
.L7:
	movl	-12(%ebp), %eax
	cmpl	-16(%ebp), %eax
	jge	.L8
	movl	-16(%ebp), %eax
	leal	0(,%eax,4), %edx
	movl	8(%ebp), %eax
	movl	(%edx,%eax), %eax
	cmpl	-8(%ebp), %eax
	jl	.L8
	leal	-16(%ebp), %eax
	decl	(%eax)
	jmp	.L7
.L8:
	movl	-12(%ebp), %eax
	leal	0(,%eax,4), %ecx
	movl	8(%ebp), %ebx
	movl	-16(%ebp), %eax
	leal	0(,%eax,4), %edx
	movl	8(%ebp), %eax
	movl	(%edx,%eax), %eax
	movl	%eax, (%ecx,%ebx)
.L9:
	movl	-12(%ebp), %eax
	cmpl	-16(%ebp), %eax
	jge	.L10
	movl	-12(%ebp), %eax
	leal	0(,%eax,4), %edx
	movl	8(%ebp), %eax
	movl	(%edx,%eax), %eax
	cmpl	-8(%ebp), %eax
	jg	.L10
	leal	-12(%ebp), %eax
	incl	(%eax)
	jmp	.L9
.L10:
	movl	-16(%ebp), %eax
	leal	0(,%eax,4), %ecx
	movl	8(%ebp), %ebx
	movl	-12(%ebp), %eax
	leal	0(,%eax,4), %edx
	movl	8(%ebp), %eax
	movl	(%edx,%eax), %eax
	movl	%eax, (%ecx,%ebx)
	jmp	.L5
.L6:
	movl	-12(%ebp), %eax
	leal	0(,%eax,4), %ecx
	movl	8(%ebp), %edx
	movl	-8(%ebp), %eax
	movl	%eax, (%ecx,%edx)
	movl	-12(%ebp), %eax
	addl	$12, %esp
	popl	%ebx
	popl	%ebp
	ret
	.size	partition, .-partition
	.section	.rodata
	.align 4
.LC4:
	.string	"Please input 10 integers to be sorted.\n"
	.text
.globl readArray
	.type	readArray, @function
readArray:
	pushl	%ebp
	movl	%esp, %ebp
	subl	$24, %esp
	movl	$0, -4(%ebp)
	movl	$.LC4, (%esp)
	call	printf
.L12:
	cmpl	$9, -4(%ebp)
	jg	.L11
	movl	-4(%ebp), %eax
	sall	$2, %eax
	addl	$data, %eax
	movl	%eax, 4(%esp)
	movl	$.LC2, (%esp)
	call	scanf
	leal	-4(%ebp), %eax
	incl	(%eax)
	jmp	.L12
.L11:
	leave
	ret
	.size	readArray, .-readArray
.globl qsort
	.type	qsort, @function
qsort:
	pushl	%ebp
	movl	%esp, %ebp
	subl	$16, %esp
	movl	12(%ebp), %eax
	cmpl	16(%ebp), %eax
	jl	.L15
	jmp	.L14
.L15:
	movl	16(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	partition
	movl	%eax, -4(%ebp)
	movl	-4(%ebp), %eax
	decl	%eax
	movl	%eax, 8(%esp)
	movl	12(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	qsort
	movl	16(%ebp), %eax
	movl	%eax, 8(%esp)
	movl	-4(%ebp), %eax
	incl	%eax
	movl	%eax, 4(%esp)
	movl	8(%ebp), %eax
	movl	%eax, (%esp)
	call	qsort
.L14:
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
	pushl	%ebp
	movl	%esp, %ebp
	subl	$24, %esp
	andl	$-16, %esp
	movl	$0, %eax
	addl	$15, %eax
	addl	$15, %eax
	shrl	$4, %eax
	sall	$4, %eax
	subl	%eax, %esp
.L17:
	cmpl	$1, i
	jle	.L19
	movl	$.LC5, (%esp)
	call	printf
	leal	-4(%ebp), %eax
	movl	%eax, 4(%esp)
	movl	$.LC2, (%esp)
	call	scanf
	cmpl	$0, -4(%ebp)
	jne	.L20
	jmp	.L16
.L20:
	cmpl	$1, -4(%ebp)
	je	.L21
	jmp	.L17
.L21:
	call	readArray
.L19:
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
	incl	i
	jmp	.L17
.L16:
	leave
	ret
	.size	main, .-main
	.section	.note.GNU-stack,"",@progbits
	.ident	"GCC: (GNU) 3.4.6"

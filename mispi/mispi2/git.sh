changeToFirst() {
	git config --local user.name first
	git config --local user.email first@red.com
}

changeToSecond() {
	git config --local user.name second
	git config --local user.email second@blue.com
}

commit() {
	cp -f ../commits/commit"$1"/* .
	git add .
	git commit --allow-empty -m "r$1"
}

branch() {
	git checkout -b "$1"
}

checkout() {
	git checkout "$1"
}

merge() {
	git merge "$1" --no-commit
}

mergestrat() {
	git merge -s "$2" "$1" --no-commit
}


#init
rm -rf git
mkdir git
cd git
git init -b "1"

#r0
changeToFirst
commit 0

#r1
changeToFirst
commit 1

#r2
changeToSecond
branch 3
commit 2

#r3
changeToFirst
branch 4
commit 3

#r4
changeToFirst
commit 4

#r5
changeToSecond
branch 2
commit 5

#r6
changeToSecond
commit 6

#r7
changeToSecond
checkout 3
commit 7

#r8
changeToFirst
checkout 1
commit 8

#r9
changeToFirst
checkout 4
commit 9

#r10-11
changeToFirst
checkout 1
commit 10
commit 11

#r12-13
changeToSecond
checkout 3
commit 12
commit 13

#r14
changeToFirst
checkout 4
commit 14

#r15
changeToFirst
checkout 1
commit 15

#r16
changeToFirst
checkout 4
commit 16

#r17
changeToFirst
checkout 1
commit 17

#r18
changeToFirst
checkout 4
commit 18

#r19
changeToFirst
checkout 1
commit 19

#r20
changeToSecond
checkout 3
commit 20

#r21
changeToFirst
checkout 4
commit 21

#r22
changeToSecond
checkout 2
merge 4
commit 22

#r23
changeToFirst
checkout 1
commit 23

#r24
changeToSecond
checkout 3
commit 24

#r25
changeToFirst
checkout 1
commit 25

#r26
changeToSecond
checkout 2
commit 26

#r27-29
changeToFirst
checkout 1
commit 27
commit 28
commit 29

#r30
changeToSecond
checkout 2
commit 30

#r31
changeToSecond
checkout 3
mergestrat 3 ours
commit 31

#r32
changeToFirst
checkout 1
mergestrat 3 ours
commit 32

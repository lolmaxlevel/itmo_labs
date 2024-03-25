changeToFirst() {
 svn propset svn:author first --revprop -r HEAD
}

changeToSecond() {
 svn propset svn:author second --revprop -r HEAD
}

commit() {
	svn rm * --force
	cp ../../commits/commit"$1"/* .
	svn add * --force # force для каталогов
	svn commit -m revision"$1"
}


branch() {
  svn copy ^/trunk ^/branches/branch"$1" -m "Creating branch $1"
}

checkout() {
  svn switch ^/branches/branch"$1"
}

#same as checkout branch0 in git
checkoutTrunk() {
  svn switch ^/trunk
}
 #postpone (по умолчанию): оставляет файлы в состоянии конфликта для последующего ручного разрешения.
 #base: выбирает базовую версию.
 #working: выбирает рабочую версию (т.е. вашу локальную версию).
 #mine-conflict: выбирает вашу версию для конфликтных изменений.
 #theirs-conflict: выбирает их версию для конфликтных изменений.
 #mine-full: выбирает вашу версию для всех изменений.
 #theirs-full: выбирает их версию для всех изменений
merge() {
  svn merge --accept working ^/branches/branch"$1"
}

# init
rm -rf svn
mkdir svn
cd svn || exit

REMOTE_URL="file://$(pwd -P)/repo"

svnadmin create repo
svn mkdir "$REMOTE_URL"/trunk "$REMOTE_URL"/branches -m "init"

# Создание хуков для разрешения изменения свойств ревизии
cd repo/hooks
echo '#!/bin/sh
exit 0' > pre-revprop-change
chmod +x pre-revprop-change
cd ../..

# Создание рабочей копии
svn checkout "$REMOTE_URL"/trunk working_copy
cd working_copy || exit

#r0
changeToFirst
commit 0

#r1
changeToFirst
commit 1

#r2
changeToSecond
branch 3
checkout 3
commit 2

#r3
changeToFirst
branch 4
checkout 4
commit 3

#r4
changeToFirst
commit 4

#r5
changeToSecond
branch 2
checkout 2
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
checkoutTrunk
commit 8

#r9
changeToFirst
checkout 4
commit 9

#r10-11
changeToFirst
checkoutTrunk
commit 10
changeToFirst
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
checkoutTrunk
commit 15

#r16
changeToFirst
checkout 4
commit 16

#r17
changeToFirst
checkoutTrunk
commit 17

#r18
changeToFirst
checkout 4
commit 18

#r19
changeToFirst
checkoutTrunk
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
checkoutTrunk
commit 23

#r24
changeToSecond
checkout 3
commit 24

#r25
changeToFirst
checkoutTrunk
commit 25

#r26
changeToSecond
checkout 2
commit 26

#r27-29
changeToFirst
checkoutTrunk
commit 27
changeToFirst
commit 28
changeToFirst
commit 29

#r30
changeToSecond
checkout 2
commit 30

#r31
changeToSecond
checkout 3
merge 3
commit 31

#r32
changeToFirst
checkoutTrunk
merge 3
commit 32
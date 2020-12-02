#!/usr/bin/env groovy

assert countValidPasswords("testinput.txt", this::validatePassword1) == 2
assert countValidPasswords("testinput.txt", this::validatePassword2) == 1

def count1 = countValidPasswords("input.txt", this::validatePassword1)
def count2 = countValidPasswords("input.txt", this::validatePassword2)

println count1
println count2


def countValidPasswords(path, passwordValidator) {
    (path as File).readLines().findAll {checkPassword(it, passwordValidator)}.size()
}

def checkPassword(passwordLine, passwordValidator) {
    (passwordLine =~ /(\d+)-(\d+) ([a-z]):\s([a-z]+)/).find { match, num1, num2, letter, password ->
        passwordValidator(num1.toInteger(), num2.toInteger(), letter, password)
    }
}

def validatePassword1(min, max, letter, password) {
    password.matches(/^([^${letter}]*$letter[^$letter]*){$min,$max}$/)
}

def validatePassword2(pos1, pos2, letter, password) {
    password.getAt(pos1-1) == letter ^ password.getAt(pos2-1) == letter
}
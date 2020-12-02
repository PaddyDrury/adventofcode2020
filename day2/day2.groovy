#!/usr/bin/env groovy

assert countValidPasswords("testinput.txt", this.&validatePassword1) == 2
assert countValidPasswords("testinput.txt", this.&validatePassword2) == 1

def count1 = countValidPasswords("input.txt", this.&validatePassword1)
def count2 = countValidPasswords("input.txt", this.&validatePassword2)

println count1
println count2


def countValidPasswords(path, passwordValidator) {
    (path as File).readLines().findAll {checkPassword(it, passwordValidator)}.size()
}

def checkPassword(passwordLine, passwordValidator) {
    (passwordLine =~ /(?<num1>\d+)-(?<num2>\d+) (?<letter>[a-z]):\s(?<password>[a-z]+)/).find { match, num1, num2, letter, password ->
        passwordValidator(num1, num2, letter, password)
    }
}

def validatePassword1(min, max, letter, password) {
    password.matches(/^([^${letter}]*$letter[^$letter]*){$min,$max}$/)
}

def validatePassword2(pos1, pos2, letter, password) {
    (password as String).getAt(pos1.toInteger()-1) == letter ^ (password as String).getAt(pos2.toInteger()-1) == letter
}
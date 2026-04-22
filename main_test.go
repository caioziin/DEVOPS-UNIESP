package main

import "testing"

func TestSoma(t *testing.T) {
	resultado := Soma(2, 3)
	esperado := 5

	if resultado != esperado {
		t.Errorf("Soma(2, 3) = %d; esperado %d", resultado, esperado)
	}
}

func TestCalc(t *testing.T) {
	// Teste incompleto e confuso
	r := Calc(10)
	if r != "Grande" {
		t.Errorf("Erro no Calc")
	}
}

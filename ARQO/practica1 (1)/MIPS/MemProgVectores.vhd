----------------------------------------------------------------------
-- Fichero: MemProgMIPS.vhd
-- Descripción: Memoria de programa para el MIPS
-- Fecha última modificación: 2013-03-19

-- Autores: Roberto Garcia, Luis Cayola
-- Asignatura: E.C. 1º grado
-- Grupo de Prácticas: 2111
-- Grupo de Teoría: 211
-- Práctica: 5
-- Ejercicio: 1
----------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.std_LOGIC_arith.ALL;
use IEEE.std_logic_unsigned.ALL;

entity MemProgMIPS is
	port (
		MemProgAddr : in std_logic_vector(31 downto 0); -- Dirección para la memoria de programa
		MemProgData : out std_logic_vector(31 downto 0) -- Código de operación
	);
end MemProgMIPS;

architecture Simple of MemProgMIPS is

begin

	LecturaMemProg: process(MemProgAddr)
	begin
		-- La memoria devuelve un valor para cada dirección.
		-- Estos valores son los códigos de programa de cada instrucción,
		-- estando situado cada uno en su dirección.
		case MemProgAddr is
			when X"00000000" => MemProgData <= X"00000820";
			when X"00000004" => MemProgData <= X"8c022078";
			when X"00000008" => MemProgData <= X"00421020";
			when X"0000000C" => MemProgData <= X"00421020";
			when X"00000010" => MemProgData <= X"10220007";
			when X"00000014" => MemProgData <= X"8c252000";
			when X"00000018" => MemProgData <= X"8c262028";
			when X"0000001C" => MemProgData <= X"00c63020";
			when X"00000020" => MemProgData <= X"00a63822";
			when X"00000024" => MemProgData <= X"ac272050";
			when X"00000028" => MemProgData <= X"20210004";
			when X"0000002C" => MemProgData <= X"08000004";
			when X"00000030" => MemProgData <= X"0800000c";	
			when others => MemProgData <= X"00000000"; -- Resto de memoria vacía
		end case;
	end process LecturaMemProg;

end Simple;


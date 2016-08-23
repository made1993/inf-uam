----------------------------------------------------------------------
-- Fichero: UnidadControl.vhd
-- Descripción: Unidad de control para el MIPS
-- Fecha última modificación: 2013-04-08

-- Autores: Luis Cayola y Roberto Garcia
-- Asignatura: E.C. 1º grado
-- Grupo de Prácticas: 2111
-- Grupo de Teoría: 211
-- Práctica: 5
-- Ejercicio: 2
----------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;

entity UnidadControlMIPS is
	port (
		OPCode : in std_logic_vector(31 downto 26);
		Funct : in std_logic_vector(5 downto 0);
		MemToReg : out std_logic;
		MemWrite : out std_logic;
		Branch : out std_logic;
		ALUControlUC : out std_logic_vector(2 downto 0);
		ALUSrc : out std_logic;
		RegDest : out std_logic;
		RegWrite : out std_logic;
		--RegToPc : out std_logic;
		--ExtCero : out std_logic;
		Jump : out std_logic;
		--PCToReg : out std_logic
	);
end UnidadControlMIPS;

architecture UC of UnidadControlMIPS is

begin

MemToReg <= '1' when OPCode="100011" else --lw
             '0'; --el resto de instrucciones
				 
MemWrite <= '1' when OPCode="101011" else --sw
             '0'; --el resto de instrucciones
				 
--Branch <= '1' when OPCode="000100" else --beq ESTA DESAPARECE
             --'0'; --el resto de instrucciones
				 
ALUSrc <= '0' when OPCode="000000" else --R-Type
             --'0' when OPCode="000100" else --beq
             '1'; --el resto de instrucciones
				 
RegDest <= '1' when OPCode="000000" else --R-Type
             '0'; --el resto de instrucciones
				 
				 
RegWrite <= '1' when OPCode="000000" else --R-Type
            '1' when OPCode="100011" else --lw
			'1' when OPCode="000101" else --bne
			'1' when OPCode="001111" else --lui
	--'1' when OPCode="001000" else --addi
				--'1' when OPCode="001100" else --andi
				--'1' when OPCode="001101" else --ori
				'1' when OPCode="001010" else --slti
				--'1' when OPCode="000011" else --jal
            '0'; --el resto de instrucciones
				 
--RegToPC <= '1' when OPCode="000000" and Funct="001000" else --R-Type (jr) TOCAR ESTO, JR DESAPARECE------
            -- '0'; --el resto de instrucciones

--ExtCero <= --'1' when OPCode="001000" else --addi
				 --'1' when OPCode="001100" else --andi
				 --'1' when OPCode="001101" else --ori
  --           '0'; --el resto de instrucciones


Jump <= '1' when OPCode="000010" else --j
			--'1' when OPCode="000011" else --jal
             '0'; --el resto de instrucciones


--PCToReg <= '1' when OPCode="000011" else --jal
            -- '0'; --el resto de instrucciones			 


ALUControlUC <= --"111" when OPCode="000000" and Funct="100111" else --R-Type (nor)
					"010" when OPCode="000000" and Funct="100010" else --R-Type (sub)
					"100" when OPCode="000000" and Funct="100100" else --R-Type (and)
					"101" when OPCode="000000" and Funct="100101" else --R-Type (or)
					"110" when OPCode="000000" and Funct="100110" else --R-Type (xor)
					--"111" when OPCode="000000" and Funct="101010" else --R-Type (slt)
					--"110" when OPCode="000100" else --beq
					--"000" when OPCode="001100" else --andi
					--"001" when OPCode="001101" else --ori
					"111" when OPCode="001010" else --slti
					"000" when OPCode="000000" and Funct="100000" else --R-Type (add)
					--"010";-- add addi lw sw 

end UC;
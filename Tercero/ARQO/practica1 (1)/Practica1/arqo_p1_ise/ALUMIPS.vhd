----------------------------------------------------------------------
-- Fichero: ALUMIPS.vhd
-- Descripción: ALU para el MIPS
-- Fecha última modificación: 1-3-2013

-- Autores: Roberto García Teodoro y Luis Cayola Pérez
-- Asignatura: E.C. 1º grado
-- Grupo de Prácticas: 2111
-- Grupo de Teoría: 211
-- Práctica: 3
-- Ejercicio: 1
----------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.std_LOGIC_arith.ALL;
use IEEE.std_logic_unsigned.ALL;

entity AluMIPS is
	port (
		Op1 : in std_logic_vector(31 downto 0); -- Operando
		Op2: in std_logic_vector(31 downto 0); -- Operando
		ALUControl: in std_logic_vector(2 downto 0); -- Operacion
		Res : out std_logic_vector(31 downto 0); -- Resultado
		Z : out std_logic -- Bandera
	);
end AluMIPS;

--Definición de la AluMIPS
architecture Compleja of AluMIPS is

signal aux,resta, Resaux: std_logic_vector(31 downto 0);
begin

process(Op1,Op2,resta) --Utilizaremos la señal resta para evaluar el signo en el slt. 
begin
   if Op1(31)='1' and Op2(31)='0' then --Primero quitamos los posibles casos que producirían un desbordamiento.
      aux<= x"00000001";

   elsif Op1(31)='0' and Op2(31)='1' then
      aux<= x"00000000";
		   elsif resta(31)='1' then
      aux<= x"00000001";
	else aux<= x"00000000";
   end if; 
end process;
		

process (ALUControl, Op1, Op2, aux)
begin
   case (ALUControl) is  --Dependiendo de la señal ALUControl, la ALU realizará una operación u otra.
		when "000" => Resaux <= Op1 + Op2;
	  
		when "010" => Resaux <= Op1 - Op2;
	  
		when "100" => Resaux <= Op1 and Op2;
 
		when "101" => Resaux <= Op1 or Op2;     

		when "110"=> Resaux <= Op1 xor Op2;
	  
     -- when "101" => Resaux <= Op1 nor Op2;
	 
      --when "111" => Resaux <= aux;

      when others => Resaux <= x"00000000";
        
   end case;
end process;
	
--Z<='1' when Resaux= x"00000000" else '0';

Res<=Resaux;--Con esta auxiliar evitamos tener que comparar una salida (que no se puede leer).
	
resta <= (Op1 - Op2);

end Compleja;


# Nguyễn Vũ Hào
# MSSV: 3122410098

import tkinter as tk
from tkinter import ttk, messagebox, scrolledtext

class TrapdoorKnapsack:
    def __init__(self, root):
        self.root = root
        self.root.title("Hệ Mã Hóa Trapdoor Knapsack")
        self.root.geometry("900x700")
        self.root.configure(bg='#f0f0f0')
        
        # Style
        style = ttk.Style()
        style.theme_use('clam')
        
        self.create_widgets()
    
    def create_widgets(self):
        # Main container
        main_frame = ttk.Frame(self.root, padding="10")
        main_frame.grid(row=0, column=0, sticky=(tk.W, tk.E, tk.N, tk.S))
        
        # Title
        title_label = tk.Label(main_frame, text="HỆ MÃ HÓA TRAPDOOR KNAPSACK", 
                               font=('Arial', 16, 'bold'), bg='#f0f0f0', fg='#2c3e50')
        title_label.grid(row=0, column=0, columnspan=2, pady=10)
        
        # Key Generation Section
        key_frame = ttk.LabelFrame(main_frame, text="Tạo Khóa", padding="10")
        key_frame.grid(row=1, column=0, columnspan=2, sticky=(tk.W, tk.E), pady=5)
        
        # Private Key (a')
        tk.Label(key_frame, text="Khóa bí mật (a'):").grid(row=0, column=0, sticky=tk.W, pady=3)
        self.private_key_entry = ttk.Entry(key_frame, width=50)
        self.private_key_entry.insert(0, "2,3,7,14,30,57,120,251")
        self.private_key_entry.grid(row=0, column=1, padx=5, pady=3)
        
        # m value
        tk.Label(key_frame, text="Giá trị m:").grid(row=1, column=0, sticky=tk.W, pady=3)
        self.m_entry = ttk.Entry(key_frame, width=50)
        self.m_entry.insert(0, "41")
        self.m_entry.grid(row=1, column=1, padx=5, pady=3)
        
        # omega value
        tk.Label(key_frame, text="Giá trị ω (omega):").grid(row=2, column=0, sticky=tk.W, pady=3)
        self.omega_entry = ttk.Entry(key_frame, width=50)
        self.omega_entry.insert(0, "491")
        self.omega_entry.grid(row=2, column=1, padx=5, pady=3)
        
        # Generate button
        gen_btn = tk.Button(key_frame, text="Tạo Khóa Công Khai", command=self.generate_public_key,
                           bg='#3498db', fg='white', font=('Arial', 10, 'bold'), cursor='hand2')
        gen_btn.grid(row=3, column=0, columnspan=2, pady=10)
        
        # Public Key display
        tk.Label(key_frame, text="Khóa công khai (a):").grid(row=4, column=0, sticky=tk.W, pady=3)
        self.public_key_entry = ttk.Entry(key_frame, width=50, state='readonly')
        self.public_key_entry.grid(row=4, column=1, padx=5, pady=3)
        
        # Encryption Section
        encrypt_frame = ttk.LabelFrame(main_frame, text="Mã Hóa", padding="10")
        encrypt_frame.grid(row=2, column=0, sticky=(tk.W, tk.E, tk.N, tk.S), pady=5, padx=5)
        
        tk.Label(encrypt_frame, text="Văn bản:").grid(row=0, column=0, sticky=tk.W, pady=3)
        self.plaintext = scrolledtext.ScrolledText(encrypt_frame, width=40, height=8, wrap=tk.WORD)
        self.plaintext.grid(row=1, column=0, pady=5)
        
        encrypt_btn = tk.Button(encrypt_frame, text="Mã Hóa", command=self.encrypt,
                               bg='#27ae60', fg='white', font=('Arial', 10, 'bold'), cursor='hand2')
        encrypt_btn.grid(row=2, column=0, pady=5)
        
        tk.Label(encrypt_frame, text="Bản mã:").grid(row=3, column=0, sticky=tk.W, pady=3)
        self.ciphertext = scrolledtext.ScrolledText(encrypt_frame, width=40, height=8, wrap=tk.WORD)
        self.ciphertext.grid(row=4, column=0, pady=5)
        
        # Decryption Section
        decrypt_frame = ttk.LabelFrame(main_frame, text="Giải Mã", padding="10")
        decrypt_frame.grid(row=2, column=1, sticky=(tk.W, tk.E, tk.N, tk.S), pady=5, padx=5)
        
        tk.Label(decrypt_frame, text="Bản mã:").grid(row=0, column=0, sticky=tk.W, pady=3)
        self.decrypt_input = scrolledtext.ScrolledText(decrypt_frame, width=40, height=8, wrap=tk.WORD)
        self.decrypt_input.grid(row=1, column=0, pady=5)
        
        decrypt_btn = tk.Button(decrypt_frame, text="Giải Mã", command=self.decrypt,
                               bg='#e74c3c', fg='white', font=('Arial', 10, 'bold'), cursor='hand2')
        decrypt_btn.grid(row=2, column=0, pady=5)
        
        tk.Label(decrypt_frame, text="Văn bản:").grid(row=3, column=0, sticky=tk.W, pady=3)
        self.decrypted_text = scrolledtext.ScrolledText(decrypt_frame, width=40, height=8, wrap=tk.WORD)
        self.decrypted_text.grid(row=4, column=0, pady=5)
        
        # Configure grid weights
        self.root.columnconfigure(0, weight=1)
        self.root.rowconfigure(0, weight=1)
        main_frame.columnconfigure(0, weight=1)
        main_frame.columnconfigure(1, weight=1)
        main_frame.rowconfigure(2, weight=1)
    
    def mod_inverse(self, a, m):
        """Tính modular inverse của a mod m"""
        a = a % m
        for x in range(1, m):
            if (a * x) % m == 1:
                return x
        return None
    
    def is_super_increasing(self, seq):
        """Kiểm tra dãy có phải siêu tăng không"""
        total = 0
        for num in seq:
            if num <= total:
                return False
            total += num
        return True
    
    def generate_public_key(self):
        try:
            # Lấy dữ liệu đầu vào
            private_key = [int(x.strip()) for x in self.private_key_entry.get().split(',')]
            m = int(self.m_entry.get())
            omega = int(self.omega_entry.get())
            
            # Kiểm tra dãy siêu tăng
            if not self.is_super_increasing(private_key):
                messagebox.showerror("Lỗi", "Khóa bí mật phải là dãy siêu tăng!\n(Mỗi phần tử > tổng các phần tử trước)")
                return
            
            # Kiểm tra m
            if m <= max(private_key):
                messagebox.showerror("Lỗi", f"m ({m}) phải lớn hơn phần tử lớn nhất ({max(private_key)})")
                return
            
            # Tính khóa công khai: a = (ω * a') mod m
            public_key = [(omega * ai) % m for ai in private_key]
            
            self.public_key_entry.config(state='normal')
            self.public_key_entry.delete(0, tk.END)
            self.public_key_entry.insert(0, ','.join(map(str, public_key)))
            self.public_key_entry.config(state='readonly')
            
            messagebox.showinfo("Thành công", "Đã tạo khóa công khai!")
            
        except ValueError:
            messagebox.showerror("Lỗi", "Vui lòng nhập các số hợp lệ!")
        except Exception as e:
            messagebox.showerror("Lỗi", f"Lỗi: {str(e)}")
    
    def encrypt(self):
        try:
            public_key_str = self.public_key_entry.get()
            if not public_key_str:
                messagebox.showerror("Lỗi", "Vui lòng tạo khóa công khai trước!")
                return
            
            public_key = [int(x.strip()) for x in public_key_str.split(',')]
            text = self.plaintext.get("1.0", tk.END).strip()
            
            if not text:
                messagebox.showerror("Lỗi", "Vui lòng nhập văn bản cần mã hóa!")
                return
            
            cipher = []
            for char in text:
                # Chuyển ký tự thành binary (8 bit)
                binary = format(ord(char), '08b')
                
                # Tính tổng knapsack
                total = sum(public_key[i] for i in range(len(binary)) if binary[i] == '1')
                cipher.append(str(total))
            
            self.ciphertext.delete("1.0", tk.END)
            self.ciphertext.insert("1.0", ', '.join(cipher))
            
            messagebox.showinfo("Thành công", "Đã mã hóa văn bản!")
            
        except Exception as e:
            messagebox.showerror("Lỗi", f"Lỗi mã hóa: {str(e)}")
    
    def decrypt(self):
        try:
            private_key = [int(x.strip()) for x in self.private_key_entry.get().split(',')]
            m = int(self.m_entry.get())
            omega = int(self.omega_entry.get())
            cipher = [int(x.strip()) for x in self.decrypt_input.get("1.0", tk.END).split(',')]
            
            # Tính ω^(-1) mod m
            omega_inv = self.mod_inverse(omega, m)
            if omega_inv is None:
                messagebox.showerror("Lỗi", "Không tìm thấy modular inverse của ω")
                return
            
            result = []
            for c in cipher:
                # Tính c' = c * ω^(-1) mod m
                c_prime = (c * omega_inv) % m
                
                # Giải bài toán knapsack với khóa bí mật
                binary = ['0'] * len(private_key)
                remaining = c_prime
                
                for i in range(len(private_key) - 1, -1, -1):
                    if remaining >= private_key[i]:
                        binary[i] = '1'
                        remaining -= private_key[i]
                
                # Chuyển binary thành ký tự
                binary_str = ''.join(binary)
                char_code = int(binary_str, 2)
                result.append(chr(char_code))
            
            self.decrypted_text.delete("1.0", tk.END)
            self.decrypted_text.insert("1.0", ''.join(result))
            
            messagebox.showinfo("Thành công", "Đã giải mã bản mã!")
            
        except Exception as e:
            messagebox.showerror("Lỗi", f"Lỗi giải mã: {str(e)}")

if __name__ == "__main__":
    root = tk.Tk()
    app = TrapdoorKnapsack(root)
    root.mainloop()